/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import com.google.gson.stream.JsonReader
import juuxel.crafty.Crafty
import juuxel.crafty.Module
import juuxel.crafty.item.CraftyBlockItem
import juuxel.crafty.util.FileName
import juuxel.crafty.util.fromJson
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.io.Reader

object BlockModule : Module {
    private val logger = LogManager.getLogger()
    override val name = "blocks"

    override fun loadContent(contentPack: String, reader: Reader, fileName: FileName) {
        try {
            val settings = Crafty.gson.fromJson<CBlockSettings>(JsonReader(reader))
            val block = settings.quirk.factory(settings)

            Registry.BLOCK.add(Identifier(contentPack, fileName.name), block)

            settings.item?.let { item ->
                Registry.ITEM.add(Identifier(contentPack, fileName.name), CraftyBlockItem(block, item))
            }
        } catch (e: Exception) {
            logger.error("Error while loading block file ${fileName.fullPath}")
            e.printStackTrace()
        }
    }
}
