/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import juuxel.crafty.item.CItemStack
import net.minecraft.item.ItemStack

object Drops {
    /**
     * If [base] is empty, returns the drops from the [settings].
     */
    fun getDrops(base: List<ItemStack>, settings: CBlockSettings) =
        base.run {
            if (isEmpty()) {
                settings.drops.map(CItemStack::toMc)
            } else this
        }
}
