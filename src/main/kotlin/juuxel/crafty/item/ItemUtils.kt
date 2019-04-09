/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import net.minecraft.text.TextComponent

object ItemUtils {
    fun buildTooltip(list: MutableList<TextComponent>, settings: CItemSettings) =
        settings.tooltip.forEach { list.add(it) }
}
