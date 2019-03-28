/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.DefaultedRegistry
import net.minecraft.util.registry.Registry

enum class Quirks(override val factory: (CItemSettings) -> Item) : Quirk {
    None(::CraftyItem);

    companion object : DefaultedRegistry<Quirk>("none") {
        init {
            Registry.register(this, "none", None)
        }

        fun fromString(str: String) = this[Identifier(str)]
    }
}
