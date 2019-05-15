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
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.chat.Component
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

    var statusEffects: Array<StatusEffectInstance> = emptyArray()
        private set

    var particles: Array<CParticle> = emptyArray()
        private set

    var chatMessages: Array<Component> = emptyArray()
        private set

    var actionBarMessage: Component? = null
        private set

    open fun run(world: World, entity: LivingEntity?, pos: BlockPos = entity!!.blockPos) {
        sound?.let {
            if (entity is PlayerEntity) {
                world.playSound(entity, pos, it, SoundCategory.BLOCKS, 1f, 1f)
            }
        }

        statusEffects.forEach {
            entity?.addPotionEffect(it)
        }

        for (p in particles) {
            world.addParticle(
                ParticleArgumentType.readParameters(StringReader(p.particle)),
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

        if (entity is PlayerEntity) {
            chatMessages.forEach {
                entity.addChatMessage(it, false)
            }

            actionBarMessage?.let {
                entity.addChatMessage(it, true)
            }
        }
    }

    class OnBlockActivation : WorldEvent() {
        var destroy: Boolean = false
            private set

        override fun run(world: World, entity: LivingEntity?, pos: BlockPos) {
            super.run(world, entity, pos)

            if (world.isClient) return
            if (destroy) {
                world.breakBlock(pos, true)
            }
        }
    }
}
