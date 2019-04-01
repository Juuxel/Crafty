/* This file is a part of the Crafty project
 * by Juuxel, licensed under the GPLv3 license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.sounds

import com.google.gson.stream.JsonReader
import juuxel.crafty.Crafty
import juuxel.crafty.Module
import juuxel.crafty.util.FileName
import juuxel.crafty.util.fromJson
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import java.io.Reader

object SoundGroupModule : Module {
    private val logger = LogManager.getLogger()
    override val name = "sound_groups"

    override fun loadContent(contentPack: String, reader: Reader, fileName: FileName) {
        try {
            val group = Crafty.gson.fromJson<SoundGroup>(JsonReader(reader))
            SoundGroup.add(Identifier(contentPack, fileName.name), group.toMc())
        } catch (e: Exception) {
            logger.error("Error while loading sound group file ${fileName.fullPath}")
            e.printStackTrace()
        }
    }
}
