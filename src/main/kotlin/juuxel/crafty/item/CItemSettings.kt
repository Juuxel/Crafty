/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import juuxel.crafty.util.CraftyContent
import net.minecraft.item.Item

class CItemSettings : CraftyContent<Item.Settings>() {
    var tab: CItemGroup? = null
        private set

    override fun toMc() = Item.Settings().apply {
        tab?.let { itemGroup(it.mc) }
    }

}
