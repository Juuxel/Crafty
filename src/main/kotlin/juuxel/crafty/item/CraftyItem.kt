/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import net.minecraft.block.Block
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.block.BlockItem
import net.minecraft.text.TextComponent
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class CraftyItem(val settings: CItemSettings) : Item(settings.toMc()) {
    override fun buildTooltip(stack: ItemStack, world: World?, list: MutableList<TextComponent>, opts: TooltipContext) =
        ItemUtils.buildTooltip(list, settings)

    override fun hasEnchantmentGlint(stack: ItemStack) =
        super.hasEnchantmentGlint(stack) || settings.glowing

    override fun use(world: World, player: PlayerEntity, hand_1: Hand?): TypedActionResult<ItemStack> {
        settings.onUse?.run(world, player)
        return super.use(world, player, hand_1)
    }

    override fun onItemFinishedUsing(stack: ItemStack, world: World, entity: LivingEntity): ItemStack {
        settings.food?.let {
            it.onConsume?.run(world, entity)
        }

        return super.onItemFinishedUsing(stack, world, entity)
    }
}

class CraftyBlockItem(block: Block, val settings: CItemSettings) : BlockItem(block, settings.toMc()) {
    override fun buildTooltip(stack: ItemStack, world: World?, list: MutableList<TextComponent>, opts: TooltipContext) =
        ItemUtils.buildTooltip(list, settings)

    override fun hasEnchantmentGlint(stack: ItemStack) =
        super.hasEnchantmentGlint(stack) || settings.glowing

    override fun use(world: World, player: PlayerEntity, hand_1: Hand?): TypedActionResult<ItemStack> {
        settings.onUse?.run(world, player)
        return super.use(world, player, hand_1)
    }

    override fun onItemFinishedUsing(stack: ItemStack, world: World, entity: LivingEntity): ItemStack {
        settings.food?.let {
            it.onConsume?.run(world, entity)
        }

        return super.onItemFinishedUsing(stack, world, entity)
    }
}
