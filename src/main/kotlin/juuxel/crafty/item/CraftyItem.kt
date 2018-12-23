/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import net.minecraft.block.Block
import net.minecraft.client.item.TooltipOptions
import net.minecraft.item.FoodItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.block.BlockItem
import net.minecraft.text.TextComponent
import net.minecraft.world.World

class CraftyItem(val settings: CItemSettings) : Item(settings.toMc()) {
    override fun buildTooltip(stack: ItemStack, world: World?, list: MutableList<TextComponent>, opts: TooltipOptions) {
        settings.tooltip.forEach { list.add(it) }
    }
}

class CraftyFoodItem(val settings: CItemSettings) :
    FoodItem(settings.food!!.hungerRestored, settings.food!!.saturation, settings.food!!.wolfFood, settings.toMc()) {
    override fun buildTooltip(stack: ItemStack, world: World?, list: MutableList<TextComponent>, opts: TooltipOptions) {
        settings.tooltip.forEach { list.add(it) }
    }
}

class CraftyBlockItem(block: Block, val settings: CItemSettings) : BlockItem(block, settings.toMc()) {
    override fun buildTooltip(stack: ItemStack, world: World?, list: MutableList<TextComponent>, opts: TooltipOptions) {
        settings.tooltip.forEach { list.add(it) }
    }
}
