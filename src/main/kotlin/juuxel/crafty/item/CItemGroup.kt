/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import net.minecraft.item.ItemGroup
import java.util.*

enum class CItemGroup(internal val mc: ItemGroup) {
    BuildingBlocks(ItemGroup.BUILDING_BLOCKS),
    Decorations(ItemGroup.DECORATIONS),
    Redstone(ItemGroup.REDSTONE),
    Transportation(ItemGroup.TRANSPORTATION),
    Misc(ItemGroup.MISC),
    Food(ItemGroup.FOOD),
    Tools(ItemGroup.TOOLS),
    Combat(ItemGroup.COMBAT),
    Brewing(ItemGroup.BREWING);

    companion object {
        private val stringMap = mapOf(
            "building_blocks" to BuildingBlocks,
            "decorations" to Decorations,
            "redstone" to Redstone,
            "transportation" to Transportation,
            "misc" to Misc,
            "food" to Food,
            "tools" to Tools,
            "combat" to Combat,
            "brewing" to Brewing
        )

        fun fromString(str: String) = stringMap[str.toLowerCase(Locale.ROOT)]
    }
}
