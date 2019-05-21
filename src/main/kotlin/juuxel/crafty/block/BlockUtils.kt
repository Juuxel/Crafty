/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import juuxel.crafty.item.CItemStack
import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.World

object BlockUtils {
    /**
     * If [base] is empty, returns the drops from the [definition].
     */
    fun getDrops(base: List<ItemStack>, definition: CBlockDefinition) =
        base.run {
            if (isEmpty()) {
                definition.drops.map(CItemStack::toMc)
            } else this
        }

    fun buildShape(shape: Array<Bounds>): VoxelShape =
        if (shape.isNotEmpty()) {
            shape.map {
                Block.createCuboidShape(it.from[0], it.from[1], it.from[2], it.to[0], it.to[1], it.to[2])
            }.reduce(VoxelShapes::union)
        } else {
            VoxelShapes.empty()
        }

    fun onActivate(world: World, player: PlayerEntity, pos: BlockPos, definition: CBlockDefinition) {
        definition.onActivate?.run(world, player, pos)
    }

    fun onBreak(world: World, pos: BlockPos, player: PlayerEntity?, definition: CBlockDefinition) {
        definition.onBreak?.run(world, player, pos)
    }

    fun onPlaced(world: World, pos: BlockPos, player: PlayerEntity?, definition: CBlockDefinition) {
        definition.onPlaced?.run(world, player, pos)
    }
}
