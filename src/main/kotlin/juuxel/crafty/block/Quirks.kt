/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import net.minecraft.block.Block
import net.minecraft.util.Identifier
import net.minecraft.util.registry.DefaultMappedRegistry
import net.minecraft.util.registry.Registry

enum class Quirks(override val factory: (CBlockSettings) -> Block) : Quirk {
    None(::CraftyBlock), Waterloggable(::CraftyWaterloggableBlock),
    Falling(::CraftyFallingBlock);

    companion object : DefaultMappedRegistry<Quirk>("none") {
        init {
            Registry.register(this, "none", None)
            Registry.register(this, "waterloggable", Waterloggable)
            Registry.register(this, "falling", Falling)
        }

        fun fromString(str: String) = this[Identifier(str)]
    }
}
