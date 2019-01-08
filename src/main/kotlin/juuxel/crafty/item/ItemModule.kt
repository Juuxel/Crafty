/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import com.google.gson.stream.JsonReader
import juuxel.crafty.Crafty
import juuxel.crafty.Module
import juuxel.crafty.util.FileName
import juuxel.crafty.util.fromJson
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.io.Reader

object ItemModule : Module {
    private val logger = LogManager.getLogger()
    override val name = "items"

    override fun loadContent(contentPack: String, reader: Reader, fileName: FileName) {
        try {
            val settings = Crafty.gson.fromJson<CItemSettings>(JsonReader(reader))
            val item = settings.quirk.factory(settings)
            Registry.ITEM.register(Identifier(contentPack, fileName.name), item)
        } catch (e: Exception) {
            logger.error("Error while loading item file ${fileName.fullPath}")
            e.printStackTrace()
        }
    }
}
