/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import juuxel.crafty.block.CBlockSettings
import juuxel.crafty.item.CItemStack
import net.minecraft.block.Block
import net.minecraft.block.dispenser.ItemDispenserBehavior
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPointerImpl
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
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

    fun onActivate(world: World, pos: BlockPos, settings: CBlockSettings) {
        if (world.isClient) return

        settings.activate.spawnItems.forEach {
            ItemDispenserBehavior.dispenseItem(world, it.toMc(), 2, Direction.UP, BlockPointerImpl(world, pos.up()))
        }

        if (settings.activate.destroy) {
            world.breakBlock(pos, true)
        }
    }
}
