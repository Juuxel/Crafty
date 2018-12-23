/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import juuxel.crafty.block.CBlockSettings
import juuxel.crafty.item.CItemStack
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.util.shape.VoxelShapes

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
}
