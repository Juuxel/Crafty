/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.sounds

import juuxel.crafty.util.Content
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

class CSoundEvents : Content<(String) -> List<SoundEvent>> {
    lateinit var events: Array<String> private set

    override fun toMc() = { namespace: String -> events.map { SoundEvent(Identifier(namespace, it)) } }
}
