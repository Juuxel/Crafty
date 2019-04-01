/* This file is a part of the Crafty project
 * by Juuxel, licensed under the GPLv3 license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import juuxel.crafty.util.Content
import juuxel.crafty.util.WorldEvent
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.text.TextComponent

class CItemSettings : Content<Item.Settings> {
    var tab: ItemGroup? = null
        private set

    var quirk: Quirk = Quirks.None
        private set

    var food: FoodSettings? = null
        private set

    /*var stackSize: Int = 64
        private set

    var durability: Int = 0
        private set*/

    var tooltip: Array<TextComponent> = emptyArray()
        private set

    var glowing: Boolean = false
        private set

    var onUse: WorldEvent? = null
        private set

    override fun toMc() = Item.Settings().apply {
        tab?.let { itemGroup(it) }/*.stackSize(stackSize).durability(durability)*/
        food?.let { food(it.toMc()) }
    }
}
