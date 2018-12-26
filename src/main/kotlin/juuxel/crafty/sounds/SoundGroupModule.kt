/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.sounds

import com.google.gson.stream.JsonReader
import juuxel.crafty.Crafty
import juuxel.crafty.Module
import juuxel.crafty.util.fromJson
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import java.nio.file.Files
import java.nio.file.Path

object SoundGroupModule : Module {
    private val logger = LogManager.getLogger()
    override val name = "sound_groups"

    override fun loadContent(contentPack: String, path: Path) {
        try {
            val group = Crafty.gson.fromJson<SoundGroup>(JsonReader(Files.newBufferedReader(path)))
            SoundGroup.register(Identifier(contentPack, path.toFile().nameWithoutExtension), group.toMc())
        } catch (e: Exception) {
            logger.error("Error while loading sound group file $path")
            e.printStackTrace()
        }
    }
}
