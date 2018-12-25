/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import com.google.gson.stream.JsonReader
import juuxel.crafty.Crafty
import juuxel.crafty.Module
import juuxel.crafty.util.fromJson
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.nio.file.Files
import java.nio.file.Path

object ItemModule : Module {
    private val logger = LogManager.getLogger()
    override val name = "items"

    override fun loadContent(path: Path) {
        try {
            val settings = Crafty.gson.fromJson<CItemSettings>(JsonReader(Files.newBufferedReader(path)))
            val item = settings.quirk.factory(settings)
            Registry.ITEM.register(settings.id, item)
        } catch (e: Exception) {
            logger.error("Error while loading item file $path")
            e.printStackTrace()
        }
    }
}
