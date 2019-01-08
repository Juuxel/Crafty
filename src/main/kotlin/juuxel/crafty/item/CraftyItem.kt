/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import juuxel.crafty.util.ItemUtils
import net.minecraft.block.Block
import net.minecraft.client.item.TooltipOptions
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.FoodItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.block.BlockItem
import net.minecraft.text.TextComponent
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class CraftyItem(val settings: CItemSettings) : Item(settings.toMc()) {
    override fun buildTooltip(stack: ItemStack, world: World?, list: MutableList<TextComponent>, opts: TooltipOptions) =
        ItemUtils.buildTooltip(list, settings)

    override fun hasEnchantmentGlow(stack: ItemStack) =
        super.hasEnchantmentGlow(stack) || settings.glowing

    override fun use(world: World, player: PlayerEntity, hand_1: Hand?): TypedActionResult<ItemStack> {
        settings.onUse?.run(world, player)
        return super.use(world, player, hand_1)
    }
}

class CraftyFoodItem(val settings: CItemSettings) :
    FoodItem(settings.food!!.hungerRestored, settings.food!!.saturation, settings.food!!.wolfFood, settings.toMc()) {
    init {
        settings.food!!.effect?.let {
            setStatusEffect(it, settings.food!!.effectChance)
        }

        if (settings.food!!.alwaysConsumable)
            setAlwaysConsumable()

        if (settings.food!!.consumeQuickly)
            setConsumeQuickly()
    }

    override fun buildTooltip(stack: ItemStack, world: World?, list: MutableList<TextComponent>, opts: TooltipOptions) =
        ItemUtils.buildTooltip(list, settings)

    override fun hasEnchantmentGlow(stack: ItemStack) =
        super.hasEnchantmentGlow(stack) || settings.glowing

    override fun onConsumed(stack: ItemStack, world: World, player: PlayerEntity) {
        super.onConsumed(stack, world, player)
        settings.food!!.onConsume?.run(world, player)
    }

    override fun use(world: World, player: PlayerEntity, hand_1: Hand?): TypedActionResult<ItemStack> {
        settings.onUse?.run(world, player)
        return super.use(world, player, hand_1)
    }
}

class CraftyBlockItem(block: Block, val settings: CItemSettings) : BlockItem(block, settings.toMc()) {
    override fun buildTooltip(stack: ItemStack, world: World?, list: MutableList<TextComponent>, opts: TooltipOptions) =
        ItemUtils.buildTooltip(list, settings)

    override fun hasEnchantmentGlow(stack: ItemStack) =
        super.hasEnchantmentGlow(stack) || settings.glowing

    override fun use(world: World, player: PlayerEntity, hand_1: Hand?): TypedActionResult<ItemStack> {
        settings.onUse?.run(world, player)
        return super.use(world, player, hand_1)
    }
}
