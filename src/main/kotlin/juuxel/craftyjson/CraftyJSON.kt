/* This file is a part of the CraftyJSON project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/CraftyJSON
 */
package juuxel.craftyjson

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import juuxel.craftyjson.block.CraftyBlock
import juuxel.craftyjson.block.CraftyBlockSettings
import juuxel.craftyjson.block.CraftyWaterloggableBlock
import juuxel.craftyjson.item.CraftyItemSettings
import juuxel.craftyjson.util.fromJson
import net.fabricmc.api.ModInitializer
import net.minecraft.item.Item
import net.minecraft.item.block.BlockItem
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object CraftyJSON : ModInitializer {
    private val logger = LogManager.getLogger()
    private val gson = Gson()
    private val directory = Paths.get("./craftyjson/")
    private const val blockDir = "blocks"
    private const val itemDir = "items"
    val craftPacks: Set<String> get() = _craftPacks
    private val _craftPacks = HashSet<String>()

    override fun onInitialize() {
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
            logger.info("[CraftyJSON] Loading craftpack $pack: $dir")
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
            val settings = gson.fromJson<CraftyBlockSettings>(JsonReader(Files.newBufferedReader(path)))
            val block =
                if (settings.material.waterloggable) CraftyWaterloggableBlock(settings)
                else CraftyBlock(settings)

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
            val settings = gson.fromJson<CraftyItemSettings>(JsonReader(Files.newBufferedReader(path)))
            val item = Item(settings.toMc())
            Registry.ITEM.register(Identifier(settings.id), item)
        } catch (e: Exception) {
            logger.error("Error while loading item file $path")
            e.printStackTrace()
        }
    }
}
