/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty

import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import juuxel.crafty.block.Quirk as BlockQuirk
import juuxel.crafty.block.CBlockSettings
import juuxel.crafty.block.CMaterial
import juuxel.crafty.compat.CompatLoader
import juuxel.crafty.item.CItemGroup
import juuxel.crafty.item.CItemSettings
import juuxel.crafty.item.Quirk as ItemQuirk
import juuxel.crafty.util.Deserializers
import juuxel.crafty.util.fromJson
import net.fabricmc.api.ModInitializer
import net.minecraft.item.Item
import net.minecraft.item.block.BlockItem
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object Crafty : ModInitializer {
    private val logger = LogManager.getLogger()
    private val gson = GsonBuilder().apply {
        registerTypeAdapter(BlockQuirk::class.java, Deserializers.BlockQuirk)
        registerTypeAdapter(ItemQuirk::class.java, Deserializers.ItemQuirk)
        registerTypeAdapter(CItemGroup::class.java, Deserializers.CreativeTab)
        registerTypeAdapter(CMaterial.SoundGroup::class.java, Deserializers.SoundGroup)
    }.create()
    private val directory = Paths.get("./crafty/")
    private const val blockDir = "blocks"
    private const val itemDir = "items"
    val craftPacks: Set<String> get() = _craftPacks
    private val _craftPacks = HashSet<String>()

    override fun onInitialize() {
        CompatLoader.load()
        checkDirs()
        loadDirectory(blockDir, ::loadBlock)
        loadDirectory(itemDir, ::loadItem)
    }

    private fun checkDirs() {
        arrayOf(directory).forEach {
            if (Files.notExists(it)) {
                Files.createDirectory(it)
            }
        }
    }

    private fun loadDirectory(dir: String, function: (Path) -> Unit) {
        Files.newDirectoryStream(directory).forEach {
            val pack = it.fileName.toString()
            _craftPacks += pack
            logger.info("[Crafty] Loading craftpack $pack: $dir")
            if (Files.isDirectory(it)) Files.newDirectoryStream(it).forEach { l2 ->
                if (Files.isDirectory(l2) && l2.fileName.toString() == dir)
                    Files.newDirectoryStream(l2).forEach { file ->
                        if (file.toFile().extension == "json") {
                            function(file)
                        }
                    }
            }
        }
    }

    private fun loadBlock(path: Path) {
        try {
            val settings = gson.fromJson<CBlockSettings>(JsonReader(Files.newBufferedReader(path)))
            val block = settings.quirk.factory(settings)

            Registry.BLOCK.register(Identifier(settings.id), block)

            settings.item?.let { item ->
                Registry.ITEM.register(Identifier(settings.id), BlockItem(block, item.toMc()))
            }
        } catch (e: Exception) {
            logger.error("Error while loading block file $path")
            e.printStackTrace()
        }
    }

    private fun loadItem(path: Path) {
        try {
            val settings = gson.fromJson<CItemSettings>(JsonReader(Files.newBufferedReader(path)))
            val item = settings.quirk.factory(settings)
            Registry.ITEM.register(Identifier(settings.id), item)
        } catch (e: Exception) {
            logger.error("Error while loading item file $path")
            e.printStackTrace()
        }
    }
}
