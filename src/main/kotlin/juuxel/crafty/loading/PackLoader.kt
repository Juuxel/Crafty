package juuxel.crafty.loading

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.effects.IO
import arrow.effects.extensions.io.monad.binding
import blue.endless.jankson.Jankson
import com.google.common.collect.ImmutableMap
import juuxel.crafty.data.BlockData
import juuxel.crafty.data.Identifier
import juuxel.crafty.data.ItemData
import juuxel.crafty.data.Shape
import juuxel.crafty.util.JsonDeserializer
import juuxel.crafty.util.JsonPrimitiveDeserializer
import org.apache.logging.log4j.LogManager
import java.io.Closeable
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.function.BiPredicate

class PackLoader(modules: Set<Module>) {
    private val modules: Map<String, Module> = with(ImmutableMap.builder<String, Module>()) {
        modules.forEach { put(it.name, it) }
        build()
    }

    fun load(gameDir: Path): IO<Unit> = binding {
        Files.newDirectoryStream(gameDir.resolve(PACK_DIRECTORY)).use { packs ->
            for (packDir in packs) {
                if (!Files.isDirectory(packDir) && !packDir.toString().endsWith(".zip", ignoreCase = true)) {
                    LOGGER.warn("Non-directory file in the pack directory: {}", packDir)
                    continue
                }

                loadPack(packDir).bind()
            }
        }
    }

    private fun loadPack(packPath: Path): IO<Unit> = binding {
        val closing = ArrayDeque<Closeable>()

        try {
            val packRoot: Either<Path, FileSystem> =
                if (Files.isDirectory(packPath))
                    Left(packPath)
                else
                    Right(
                        FileSystems.newFileSystem(packPath.toUri(), mapOf("create" to "true"))
                            .also(closing::push)
                    )

            fun getPath(path: String) =
                packRoot.fold(
                    { it.resolve(path) },
                    { it.getPath(path) }
                )

            val packMetaPath = getPath("crafty.pack.json")
            if (Files.notExists(packMetaPath)) {
                LOGGER.debug("[Crafty] Skipping pack path {}, does not contain crafty.pack.json", packPath)
                return@binding
            }

            val packMeta = JANKSON.fromJson(
                Files.readAllLines(packMetaPath).joinToString("\n"),
                PackMetadata::class.java
            ) ?: run {
                LOGGER.error("[Crafty] Couldn't deserialize pack metadata for pack at {}, see log", packPath)
                return@binding
            }

            for ((moduleName, module) in modules) {
                try {
                    val modulePath = getPath(moduleName)

                    if (Files.notExists(modulePath)) continue

                    LOGGER.info("[Crafty] Loading content pack {}: module {}", packMeta.name, moduleName)
                    val contentPaths = Files.find(modulePath, Int.MAX_VALUE, BiPredicate { path, _ ->
                        val fileName = path.fileName.toString()
                        fileName.endsWith(".json5", ignoreCase = true) || fileName.endsWith(".json", ignoreCase = true)
                    })
                    for (contentPath in contentPaths) {
                        try {
                            val fileName = contentPath.fileName.toString()
                            module.load(
                                contentPath,
                                Identifier(packMeta.id, fileName.substringBeforeLast('.').toLowerCase(Locale.ROOT)),
                                JANKSON
                            ).bind()
                        } catch (e: Exception) {
                            LOGGER.error(
                                "[Crafty] Error while loading file {} in pack {}",
                                runCatching { packPath.relativize(contentPath) }
                                    .getOrElse { contentPath },
                                packMeta.name,
                                e
                            )
                        }
                    }
                } catch (e: Exception) {
                    LOGGER.error("[Crafty] Error while loading module {} for pack {}", moduleName, packMeta.name, e)
                }
            }
        } catch (e: Exception) {
            LOGGER.error("[Crafty] Error while loading content pack from file {}", packPath, e)
        } finally {
            closing.descendingIterator().forEach {
                // use it.use {} for properly suppressing exceptions
                it.use {}
            }
        }
    }

    companion object {
        private const val PACK_DIRECTORY = "crafty"
        private val LOGGER = LogManager.getLogger()
        private val JANKSON = Jankson.builder()
            .registerTypeAdapter(PackMetadata)
            .registerTypeAdapter(BlockData)
            .registerTypeAdapter(BlockData.Settings)
            .registerTypeAdapter(ItemData)
            .registerTypeAdapter(Shape)
            .registerPrimitiveTypeAdapter(Identifier)
            .build()

        private inline fun <reified T> Jankson.Builder.registerTypeAdapter(deserializer: JsonDeserializer<T>) =
            registerTypeAdapter(T::class.java, deserializer.toJanksonDeserializer())

        private inline fun <reified T> Jankson.Builder.registerPrimitiveTypeAdapter(deserializer: JsonPrimitiveDeserializer<T>) =
            registerPrimitiveTypeAdapter(T::class.java, deserializer.toJanksonDeserializer())
    }
}
