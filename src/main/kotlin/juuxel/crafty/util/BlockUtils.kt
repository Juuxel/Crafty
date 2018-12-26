/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import juuxel.crafty.block.CBlockSettings
import juuxel.crafty.item.CItemStack
import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.World

object BlockUtils {
    /**
     * If [base] is empty, returns the drops from the [settings].
     */
    fun getDrops(base: List<ItemStack>, settings: CBlockSettings) =
        base.run {
            if (isEmpty()) {
                settings.drops.map(CItemStack::toMc)
            } else this
        }

    fun getShape(settings: CBlockSettings) =
        settings.shape.map {
            Block.createCubeShape(it.from[0], it.from[1], it.from[2], it.to[0], it.to[1], it.to[2])
        }.reduce(VoxelShapes::union)

    fun onActivate(world: World, player: PlayerEntity, pos: BlockPos, settings: CBlockSettings) {
        settings.onActivate?.run(world, player, pos)
    }

    fun onBreak(world: World, pos: BlockPos, player: PlayerEntity?, settings: CBlockSettings) {
        settings.onBreak?.run(world, player, pos)
    }

    fun onPlaced(world: World, pos: BlockPos, player: PlayerEntity?, settings: CBlockSettings) {
        settings.onPlaced?.run(world, player, pos)
    }
}
