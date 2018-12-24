/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import juuxel.crafty.item.CItemStack
import net.minecraft.block.dispenser.ItemDispenserBehavior
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.BlockPointerImpl
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

open class WorldEvent {
    var sound: SoundEvent? = null
        private set

    var spawnItems: Array<CItemStack> = emptyArray()
        private set

    fun run(world: World, player: PlayerEntity, pos: BlockPos = player.pos) {
        sound?.let {
            world.playSound(player, pos, it, SoundCategory.BLOCK, 1f, 1f)
        }

        // Server-side code below
        if (world.isClient) return

        spawnItems.forEach {
            ItemDispenserBehavior.dispenseItem(world, it.toMc(), 2, Direction.UP, BlockPointerImpl(world, pos.up()))
        }
    }
}
