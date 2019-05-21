/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import net.minecraft.block.*
import net.minecraft.entity.FallingBlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.EntityContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateFactory
import net.minecraft.state.property.Properties
import net.minecraft.tag.FluidTags
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.loot.context.LootContext

open class CraftyBlock(val definition: CBlockDefinition) : Block(definition.toMc()) {
    override fun getOutlineShape(
        state: BlockState?,
        view: BlockView?,
        pos: BlockPos?,
        context: EntityContext?
    ) = definition.builtOutlineShape

    override fun getCollisionShape(
        state: BlockState?,
        view: BlockView?,
        pos: BlockPos?,
        context: EntityContext?
    ) = definition.builtCollisionShape ?: super.getCollisionShape(state, view, pos, context)

    override fun getDroppedStacks(
        state: BlockState?,
        builder: LootContext.Builder?
    ) = BlockUtils.getDrops(super.getDroppedStacks(state, builder), definition)

    override fun activate(
        state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hitResult: BlockHitResult
    ): Boolean {
        BlockUtils.onActivate(world, player, pos, definition)

        return super.activate(
            state,
            world,
            pos,
            player,
            hand,
            hitResult
        )
    }

    override fun emitsRedstonePower(state: BlockState) = definition.settings.redstonePower > 0
    override fun getWeakRedstonePower(
        state: BlockState?,
        view: BlockView?,
        pos: BlockPos?,
        direction: Direction?
    ) = definition.settings.redstonePower

    override fun onBreak(
        world: World,
        pos: BlockPos,
        state: BlockState?,
        player: PlayerEntity?
    ) {
        super.onBreak(world, pos, state, player)
        BlockUtils.onBreak(world, pos, player, definition)
    }

    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState?,
        entity: LivingEntity?,
        stack: ItemStack?
    ) {
        super.onPlaced(world, pos, state, entity, stack)
        BlockUtils.onPlaced(world, pos, entity as? PlayerEntity, definition)
    }

    override fun getRenderLayer() = definition.settings.renderLayer
}

class CraftyWaterloggableBlock(definition: CBlockDefinition) : CraftyBlock(definition), Waterloggable {
    init {
        defaultState = stateFactory.defaultState.with(Properties.WATERLOGGED, false)
    }

    override fun getFluidState(state: BlockState) =
        if (state[Properties.WATERLOGGED]) Fluids.WATER.getStill(true)
        else Fluids.EMPTY.defaultState

    override fun appendProperties(builder: StateFactory.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(Properties.WATERLOGGED)
    }

    override fun getPlacementState(context: ItemPlacementContext): BlockState? =
        defaultState.with(Properties.WATERLOGGED, context.world.getFluidState(context.blockPos).fluid.matches(
            FluidTags.WATER))
}

open class CraftyFallingBlock(val definition: CBlockDefinition) : FallingBlock(definition.toMc()) {
    override fun getOutlineShape(
        state: BlockState?,
        view: BlockView?,
        pos: BlockPos?,
        context: EntityContext?
    ) = definition.builtOutlineShape

    override fun getCollisionShape(
        state: BlockState?,
        view: BlockView?,
        pos: BlockPos?,
        context: EntityContext?
    ) = definition.builtCollisionShape ?: super.getCollisionShape(state, view, pos, context)

    override fun getDroppedStacks(
        state: BlockState?,
        builder: LootContext.Builder?
    ) = BlockUtils.getDrops(super.getDroppedStacks(state, builder), definition)

    override fun activate(
        state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hitResult: BlockHitResult
    ): Boolean {
        BlockUtils.onActivate(world, player, pos, definition)

        return super.activate(
            state,
            world,
            pos,
            player,
            hand,
            hitResult
        )
    }

    override fun emitsRedstonePower(state: BlockState) = definition.settings.redstonePower > 0
    override fun getWeakRedstonePower(
        state: BlockState?,
        view: BlockView?,
        pos: BlockPos?,
        direction: Direction?
    ) = definition.settings.redstonePower

    override fun onBreak(
        world: World,
        pos: BlockPos,
        state: BlockState?,
        player: PlayerEntity?
    ) {
        super.onBreak(world, pos, state, player)
        BlockUtils.onBreak(world, pos, player, definition)
    }

    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState?,
        entity: LivingEntity?,
        stack: ItemStack?
    ) {
        super.onPlaced(world, pos, state, entity, stack)
        BlockUtils.onPlaced(world, pos, entity as? PlayerEntity, definition)
    }

    override fun configureFallingBlockEntity(entity: FallingBlockEntity) {
        super.configureFallingBlockEntity(entity)

        if (definition.falling?.hurtsEntities == true)
            entity.setHurtEntities(true)
    }

    override fun onLanding(
        world: World,
        pos: BlockPos,
        fallingBlockState: BlockState,
        currentStateInPos: BlockState
    ) {
        definition.falling?.onLanding?.run(world, null, pos)

        if (world.random.nextDouble() < definition.falling?.breakingChance ?: 0.0) {
            definition.falling?.onBreaking?.run(world, null, pos)

            definition.falling?.breakingState?.let {
                if (world is ServerWorld) {
                    it.setBlockState(world, pos, 3)
                }
            }
        }
    }

    override fun getRenderLayer() = definition.settings.renderLayer
}
