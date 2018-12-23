/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.DefaultMappedRegistry
import net.minecraft.util.registry.Registry

enum class Quirks(override val factory: (CItemSettings) -> Item) : Quirk {
    None(::CraftyItem),
    Food(::CraftyFoodItem);

    companion object : DefaultMappedRegistry<Quirk>("none") {
        init {
            Registry.register(this, "none", None)
            Registry.register(this, "food", Food)
        }

        fun fromString(str: String) = this[Identifier(str)]
    }
}
