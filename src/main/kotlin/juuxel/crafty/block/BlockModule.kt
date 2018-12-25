/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import com.google.gson.stream.JsonReader
import juuxel.crafty.Crafty
import juuxel.crafty.Module
import juuxel.crafty.item.CraftyBlockItem
import juuxel.crafty.util.fromJson
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.nio.file.Files
import java.nio.file.Path

object BlockModule : Module {
    private val logger = LogManager.getLogger()
    override val name = "blocks"

    override fun loadContent(path: Path) {
        try {
            val settings = Crafty.gson.fromJson<CBlockSettings>(JsonReader(Files.newBufferedReader(path)))
            val block = settings.quirk.factory(settings)

            Registry.BLOCK.register(settings.id, block)

            settings.item?.let { item ->
                Registry.ITEM.register(settings.id, CraftyBlockItem(block, item))
            }
        } catch (e: Exception) {
            logger.error("Error while loading block file $path")
            e.printStackTrace()
        }
    }
}
