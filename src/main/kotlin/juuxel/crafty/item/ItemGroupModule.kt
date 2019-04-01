/* This file is a part of the Crafty project
 * by Juuxel, licensed under the GPLv3 license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import com.google.gson.stream.JsonReader
import juuxel.crafty.Crafty
import juuxel.crafty.Module
import juuxel.crafty.util.FileName
import juuxel.crafty.util.fromJson
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import java.io.Reader

object ItemGroupModule : Module {
    private val logger = LogManager.getLogger()
    override val name = "item_groups"

    override fun loadContent(contentPack: String, reader: Reader, fileName: FileName) {
        try {
            val settings = Crafty.gson.fromJson<CItemGroup>(JsonReader(reader))
            settings.toMc()(Identifier(contentPack, fileName.name))
        } catch (e: Exception) {
            logger.error("Error while loading item group file ${fileName.fullPath}")
            e.printStackTrace()
        }
    }
}
