/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.sounds

import com.google.gson.stream.JsonReader
import juuxel.crafty.Crafty
import juuxel.crafty.Module
import juuxel.crafty.util.FileName
import juuxel.crafty.util.fromJson
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.io.Reader

object SoundEventModule : Module {
    private val logger = LogManager.getLogger()
    override val name = "sound_events"

    override fun loadContent(contentPack: String, reader: Reader, fileName: FileName) {
        try {
            val sounds = Crafty.gson.fromJson<CSoundEvents>(JsonReader(reader))

            for ((id, sound) in sounds.events.zip(sounds.toMc()(contentPack))) {
                Registry.register(
                    Registry.SOUND_EVENT,
                    Identifier(contentPack, id),
                    sound
                )
            }
        } catch (e: Exception) {
            logger.error("Error while loading sound event file ${fileName.fullPath}")
            e.printStackTrace()
        }
    }
}
