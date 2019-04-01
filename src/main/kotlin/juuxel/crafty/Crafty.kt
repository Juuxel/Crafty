/* This file is a part of the Crafty project
 * by Juuxel, licensed under the GPLv3 license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty

import com.google.gson.GsonBuilder
import de.tudresden.inf.lat.jsexp.SexpFactory
import juuxel.crafty.block.BlockModule
import juuxel.crafty.compat.CompatLoader
import juuxel.crafty.item.*
import juuxel.crafty.sounds.SoundEventModule
import juuxel.crafty.sounds.SoundGroupModule
import juuxel.crafty.util.Deserializers
import juuxel.crafty.util.FileName
import juuxel.crafty.util.SexpConverter
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
    private val directory = File(FabricLoader.getInstance().gameDirectory, "./crafty/").toPath()
    private val contentPacks = HashSet<String>()
    private val modules = mutableSetOf(ItemGroupModule, SoundEventModule, SoundGroupModule, BlockModule, ItemModule/*, PaintingModule*/)

    override fun onInitialize() {
        CompatLoader.load()
        checkDirs()
        modules.forEach(::loadModule)
    }

    private fun checkDirs() {
        arrayOf(directory).forEach {
            if (Files.notExists(it)) {
                Files.createDirectory(it)
            }
        }
    }

    private fun loadModule(module: Module) {
        val dir = module.name

        Files.newDirectoryStream(directory).forEach {
            val pack = it.fileName.toString().toLowerCase(Locale.ROOT)
            contentPacks += pack
            logger.info("[Crafty] Loading content pack $pack: $dir")
            if (Files.isDirectory(it)) Files.newDirectoryStream(it).forEach { l2 ->
                if (Files.isDirectory(l2) && l2.fileName.toString() == dir)
                    Files.newDirectoryStream(l2).forEach { file ->
                        when (file.toFile().extension) {
                            "sexp" -> module.loadContent(
                                pack,
                                StringReader(
                                    SexpConverter.sexpToJson(
                                        SexpFactory.parse(Files.newBufferedReader(file))
                                    ).toString())
                                ,
                                FileName.fromFile(file.toFile())
                            )
                        }
                    }
            }
        }
    }

    fun registerModule(module: Module) {
        modules += module
    }
}
