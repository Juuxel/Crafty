/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.compat

import juuxel.crafty.block.CBlockDefinition
import juuxel.crafty.block.CraftyBlock
import juuxel.crafty.block.Quirk
import virtuoel.towelette.api.Fluidloggable

class ToweletteFluidloggableBlock(definition: CBlockDefinition) : CraftyBlock(definition), Fluidloggable {
    companion object : Quirk {
        override val factory = ::ToweletteFluidloggableBlock
    }
}
