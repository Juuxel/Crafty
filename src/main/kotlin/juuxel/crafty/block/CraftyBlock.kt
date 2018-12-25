/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import juuxel.crafty.util.BlockUtils
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FallingBlock
import net.minecraft.block.Waterloggable
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateFactory
import net.minecraft.state.property.Properties
import net.minecraft.tag.FluidTags
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.loot.context.LootContext

open class CraftyBlock(val settings: CBlockSettings) : Block(settings.toMc()) {
    override fun getBoundingShape(
        blockState_1: BlockState?,
        blockView_1: BlockView?,
        blockPos_1: BlockPos?
    ) = BlockUtils.getShape(settings)

    override fun getDroppedStacks(
        state: BlockState?,
        builder: LootContext.Builder?
    ) = BlockUtils.getDrops(super.getDroppedStacks(state, builder), settings)

    override fun activate(
        state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, direction: Direction, f1: Float, f2: Float, f3: Float
    ): Boolean {
        BlockUtils.onActivate(world, player, pos, settings)

        return super.activate(
            state,
            world,
            pos,
            player,
            hand,
            direction,
            f1,
            f2,
            f3
        )
    }

    override fun emitsRedstonePower(state: BlockState) = settings.material.redstonePower > 0
    override fun getWeakRedstonePower(
        state: BlockState?,
        view: BlockView?,
        pos: BlockPos?,
        direction: Direction?
    ) = settings.material.redstonePower

    override fun onBreak(
        world: World,
        pos: BlockPos,
        state: BlockState?,
        player: PlayerEntity?
    ) {
        super.onBreak(world, pos, state, player)
        BlockUtils.onBreak(world, pos, player, settings)
    }

    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState?,
        entity: LivingEntity?,
        stack: ItemStack?
    ) {
        super.onPlaced(world, pos, state, entity, stack)
        BlockUtils.onPlaced(world, pos, entity as? PlayerEntity, settings)
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
    ) = BlockUtils.getShape(settings)

    override fun getDroppedStacks(
        state: BlockState?,
        builder: LootContext.Builder?
    ) = BlockUtils.getDrops(super.getDroppedStacks(state, builder), settings)

    override fun activate(
        state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, direction: Direction, f1: Float, f2: Float, f3: Float
    ): Boolean {
        BlockUtils.onActivate(world, player, pos, settings)

        return super.activate(
            state,
            world,
            pos,
            player,
            hand,
            direction,
            f1,
            f2,
            f3
        )
    }

    override fun emitsRedstonePower(state: BlockState) = settings.material.redstonePower > 0
    override fun getWeakRedstonePower(
        state: BlockState?,
        view: BlockView?,
        pos: BlockPos?,
        direction: Direction?
    ) = settings.material.redstonePower

    override fun onBreak(
        world: World,
        pos: BlockPos,
        state: BlockState?,
        player: PlayerEntity?
    ) {
        super.onBreak(world, pos, state, player)
        BlockUtils.onBreak(world, pos, player, settings)
    }

    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState?,
        entity: LivingEntity?,
        stack: ItemStack?
    ) {
        super.onPlaced(world, pos, state, entity, stack)
        BlockUtils.onPlaced(world, pos, entity as? PlayerEntity, settings)
    }
}
