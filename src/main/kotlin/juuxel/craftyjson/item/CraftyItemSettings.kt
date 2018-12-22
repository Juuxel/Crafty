/* This file is a part of the CraftyJSON project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/CraftyJSON
 */
package juuxel.craftyjson.item

import juuxel.craftyjson.util.CraftyContent
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup

class CraftyItemSettings : CraftyContent<Item.Settings>() {
    var tab: Tab? = null
        private set

    override fun toMc() = Item.Settings().apply {
        tab?.let { itemGroup(it.mc) }
    }

    enum class Tab(internal val mc: ItemGroup) {
        BuildingBlocks(ItemGroup.BUILDING_BLOCKS),
        Decorations(ItemGroup.DECORATIONS),
        Redstone(ItemGroup.REDSTONE),
        Transportation(ItemGroup.TRANSPORTATION),
        Misc(ItemGroup.MISC),
        Food(ItemGroup.FOOD),
        Tools(ItemGroup.TOOLS),
        Combat(ItemGroup.COMBAT),
        Brewing(ItemGroup.BREWING)
    }
}
