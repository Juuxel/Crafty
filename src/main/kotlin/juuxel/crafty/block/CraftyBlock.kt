/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import juuxel.crafty.item.CItemStack
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FallingBlock
import net.minecraft.block.Waterloggable
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateFactory
import net.minecraft.state.property.Properties
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.loot.context.LootContext

open class CraftyBlock(val settings: CBlockSettings) : Block(settings.toMc()) {
    override fun getBoundingShape(
        blockState_1: BlockState?,
        blockView_1: BlockView?,
        blockPos_1: BlockPos?
    ) = settings.shape.map {
        Block.createCubeShape(it.from[0], it.from[1], it.from[2], it.to[0], it.to[1], it.to[2])
    }.reduce(VoxelShapes::union)

    override fun getDroppedStacks(
        state: BlockState?,
        builder: LootContext.Builder?
    ): List<ItemStack> = super.getDroppedStacks(state, builder).run {
        if (isEmpty()) {
            settings.drops.map(CItemStack::toMc)
        } else this
    }
}

class CraftyWaterloggableBlock(settings: CBlockSettings) : CraftyBlock(settings), Waterloggable {
    init {
        defaultState = stateFactory.defaultState.with(Properties.WATERLOGGED, false)
    }

    override fun getFluidState(state: BlockState) =
        if (state[Properties.WATERLOGGED]) Fluids.WATER.getState(true)
        else Fluids.EMPTY.defaultState

    override fun appendProperties(builder: StateFactory.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.with(Properties.WATERLOGGED)
    }

    override fun getPlacementState(context: ItemPlacementContext): BlockState? =
        defaultState.with(Properties.WATERLOGGED, context.world.getFluidState(context.pos).fluid.matches(
            FluidTags.WATER))
}

open class CraftyFallingBlock(val settings: CBlockSettings) : FallingBlock(settings.toMc()) {
    override fun getBoundingShape(
        blockState_1: BlockState?,
        blockView_1: BlockView?,
        blockPos_1: BlockPos?
    ) = settings.shape.map {
        Block.createCubeShape(it.from[0], it.from[1], it.from[2], it.to[0], it.to[1], it.to[2])
    }.reduce(VoxelShapes::union)

    override fun getDroppedStacks(
        state: BlockState?,
        builder: LootContext.Builder?
    ): List<ItemStack> = super.getDroppedStacks(state, builder).run {
        if (isEmpty()) {
            settings.drops.map(CItemStack::toMc)
        } else this
    }
}
