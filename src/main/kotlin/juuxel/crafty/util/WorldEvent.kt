/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import com.mojang.brigadier.StringReader
import juuxel.crafty.item.CItemStack
import juuxel.crafty.particle.CParticle
import net.minecraft.block.dispenser.ItemDispenserBehavior
import net.minecraft.command.arguments.ParticleArgumentType
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.text.TextComponent
import net.minecraft.util.math.BlockPointerImpl
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

open class WorldEvent {
    var sound: SoundEvent? = null
        private set

    var spawnItems: Array<CItemStack> = emptyArray()
        private set

    var statusEffects: Array<StatusEffectInstance> = emptyArray()
        private set

    var particles: Array<CParticle> = emptyArray()
        private set

    var chatMessages: Array<TextComponent> = emptyArray()
        private set

    var actionBarMessage: TextComponent? = null
        private set

    open fun run(world: World, player: PlayerEntity?, pos: BlockPos = player!!.pos) {
        sound?.let {
            world.playSound(player, pos, it, SoundCategory.BLOCK, 1f, 1f)
        }

        statusEffects.forEach {
            player?.addPotionEffect(it)
        }

        for (p in particles) {
            world.addParticle(
                ParticleArgumentType.method_9418(StringReader(p.particle)),
                pos.x.toDouble() + p.xOffset + 0.5,
                pos.y.toDouble() + p.yOffset,
                pos.z.toDouble() + p.zOffset + 0.5,
                p.velocityX, p.velocityY, p.velocityZ
            )
        }

        // Server-side code below
        if (world.isClient) return

        spawnItems.forEach {
            ItemDispenserBehavior.dispenseItem(world, it.toMc(), 2, Direction.UP, BlockPointerImpl(world, pos.up()))
        }

        chatMessages.forEach {
            player?.addChatMessage(it, false)
        }

        actionBarMessage?.let {
            player?.addChatMessage(it, true)
        }
    }

    class OnBlockActivation : WorldEvent() {
        var destroy: Boolean = false
            private set

        override fun run(world: World, player: PlayerEntity?, pos: BlockPos) {
            super.run(world, player, pos)

            if (world.isClient) return
            if (destroy) {
                world.breakBlock(pos, true)
            }
        }
    }
}
