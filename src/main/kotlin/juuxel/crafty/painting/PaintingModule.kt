/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.painting

import com.google.gson.stream.JsonReader
import juuxel.crafty.Crafty
import juuxel.crafty.Module
import juuxel.crafty.util.FileName
import juuxel.crafty.util.fromJson
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.io.Reader

object PaintingModule : Module {
    private val logger = LogManager.getLogger()
    override val name = "paintings"

    override fun loadContent(contentPack: String, reader: Reader, fileName: FileName) {
        try {
            val motive = Crafty.gson.fromJson<CraftyPainting>(JsonReader(reader))
            Registry.MOTIVE.add(Identifier(contentPack, fileName.name), motive.toMc())
        } catch (e: Exception) {
            logger.error("Error while loading painting file ${fileName.fullPath}")
            e.printStackTrace()
        }
    }
}
