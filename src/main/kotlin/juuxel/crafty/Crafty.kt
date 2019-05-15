/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty

import a2u.tn.utils.json.JsonParser
import a2u.tn.utils.json.JsonSerializer
import com.google.gson.GsonBuilder
import juuxel.crafty.block.BlockModule
import juuxel.crafty.compat.CompatLoader
import juuxel.crafty.item.*
import juuxel.crafty.sounds.SoundEventModule
import juuxel.crafty.sounds.SoundGroupModule
import juuxel.crafty.util.Deserializers
import juuxel.crafty.util.FileName
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.StringReader
import java.nio.file.Files
import java.util.*
import juuxel.crafty.block.Quirk as BlockQuirk
import juuxel.crafty.item.Quirk as ItemQuirk

object Crafty : ModInitializer {
    private val logger = LogManager.getLogger()
    internal val gson = GsonBuilder().apply(Deserializers::applyToGson).create()
    private val contentPackDir = FabricLoader.getInstance().gameDirectory.toPath().resolve("crafty")
    private val contentPacks = HashSet<String>()
    private val modules = mutableSetOf(ItemGroupModule, SoundEventModule, SoundGroupModule, BlockModule, ItemModule/*, PaintingModule*/)

    override fun onInitialize() {
        CompatLoader.load()
        checkDirs()
        modules.forEach(::loadModule)
    }

    private fun checkDirs() {
        arrayOf(contentPackDir).forEach {
            if (Files.notExists(it)) {
                Files.createDirectory(it)
            } else if (!Files.isDirectory(it)) {
                logger.error("[Crafty] The content pack directory ($it) is not a directory!")
            }
        }
    }

    // TODO: Make loading iterate on packs, not modules
    private fun loadModule(module: Module) {
        val moduleName = module.name

        for (packFile in Files.newDirectoryStream(contentPackDir)) {
            if (!Files.isDirectory(packFile)) {
                logger.debug("[Crafty] Skipping non-directory file $packFile while loading module ${module.name}")
                continue
            }

            val pack = packFile.fileName.toString().toLowerCase(Locale.ROOT)
            contentPacks += pack
            val modulePath = packFile.resolve(moduleName)
            val isModulePresent = Files.isDirectory(modulePath)
            logger.info("[Crafty] Loading content pack $pack: $moduleName${if (!isModulePresent) " [not found]" else ""}")

            if (!isModulePresent) continue

            Files.newDirectoryStream(modulePath).forEach { file ->
                when (file.toFile().extension) {
                    "json" -> module.loadContent(
                        pack,
                        Files.newBufferedReader(file),
                        FileName.fromFile(file.toFile())
                    )

                    "json5" -> module.loadContent(
                        pack,
                        StringReader(
                            JsonSerializer.toJson(
                                JsonParser.parse(
                                    file.toFile().readText()
                                )
                            )
                        ),
                        FileName.fromFile(file.toFile())
                    )
                }
            }
        }
    }

    fun registerModule(module: Module) {
        modules += module
    }
}
