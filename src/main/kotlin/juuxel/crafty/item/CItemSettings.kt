/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import juuxel.crafty.util.Content
import net.minecraft.item.Item

class CItemSettings : Content<Item.Settings>() {
    var tab: CItemGroup? = null
        private set

    var quirk: Quirk = Quirks.None
        private set

    var food: FoodSettings? = null
        private set

    /*var stackSize: Int = 64
        private set

    var durability: Int = 0
        private set*/

    override fun toMc() = Item.Settings().apply {
        tab?.let { itemGroup(it.mc)/*.stackSize(stackSize).durability(durability)*/ }
    }
}
