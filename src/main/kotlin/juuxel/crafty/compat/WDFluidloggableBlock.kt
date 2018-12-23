/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.compat

import juuxel.crafty.block.CBlockSettings
import juuxel.crafty.block.CraftyBlock
import juuxel.crafty.block.Quirk
import juuxel.watereddown.api.FluidProperty
import juuxel.watereddown.api.Fluidloggable
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory

class WDFluidloggableBlock(settings: CBlockSettings) : CraftyBlock(settings), Fluidloggable {
    init {
        defaultState = stateFactory.defaultState.with(FluidProperty.FLUID, FluidProperty.EMPTY)
    }

    override fun appendProperties(builder: StateFactory.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.with(FluidProperty.FLUID)
    }

    override fun getPlacementState(context: ItemPlacementContext): BlockState {
        return super.getPlacementState(context)!!
            .with(FluidProperty.FLUID, FluidProperty.Wrapper(context.world.getFluidState(context.pos).fluid))
    }

    companion object : Quirk {
        override val factory = ::WDFluidloggableBlock
    }
}
