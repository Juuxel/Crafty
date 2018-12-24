/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class WorldEvent {
    var sound: SoundEvent? = null
        private set

    fun run(world: World, player: PlayerEntity, pos: BlockPos = player.pos) {
        sound?.let {
            world.playSound(player, pos, it, SoundCategory.BLOCK, 1f, 1f)
        }
    }
}
