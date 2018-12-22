/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import net.minecraft.item.FoodItem
import net.minecraft.item.Item
import org.apache.logging.log4j.LogManager
import java.util.*

enum class Quirks(override val factory: (CItemSettings) -> Item) : Quirk {
    None({ Item(it.toMc()) }),
    Food({ FoodItem(it.food!!.hungerRestored, it.food!!.saturation, it.food!!.wolfFood, it.toMc()) });

    companion object {
        private val logger = LogManager.getLogger()
        private val stringMap: MutableMap<String, Quirk> = mutableMapOf(
            "none" to None,
            "food" to Food
        )

        fun register(name: String, quirk: Quirk) {
            if (!stringMap.containsKey(name))
                stringMap[name] = quirk
            else
                logger.warn("[Crafty] Trying to register quirk $quirk, name $name already registered")
        }

        fun fromString(str: String) =
            stringMap[str.toLowerCase(Locale.ROOT)] ?: throw IllegalArgumentException("Quirk $str not found")
    }
}
