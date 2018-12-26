/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.painting

import com.google.gson.stream.JsonReader
import juuxel.crafty.Crafty
import juuxel.crafty.Module
import juuxel.crafty.util.fromJson
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.nio.file.Files
import java.nio.file.Path

object PaintingModule : Module {
    private val logger = LogManager.getLogger()
    override val name = "paintings"

    override fun loadContent(contentPack: String, path: Path) {
        try {
            val motive = Crafty.gson.fromJson<CraftyPainting>(JsonReader(Files.newBufferedReader(path)))
            Registry.MOTIVE.register(Identifier(contentPack, path.toFile().nameWithoutExtension), motive.toMc())
        } catch (e: Exception) {
            logger.error("Error while loading painting file $path")
            e.printStackTrace()
        }
    }
}
