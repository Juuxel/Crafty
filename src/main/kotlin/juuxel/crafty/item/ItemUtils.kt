/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import net.minecraft.network.chat.Component

object ItemUtils {
    fun buildTooltip(list: MutableList<Component>, settings: CItemSettings) =
        settings.tooltip.forEach { list.add(it) }
}
