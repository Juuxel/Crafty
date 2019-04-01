/* This file is a part of the Crafty project
 * by Juuxel, licensed under the GPLv3 license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import juuxel.crafty.item.CItemSettings
import net.minecraft.text.TextComponent

object ItemUtils {
    fun buildTooltip(list: MutableList<TextComponent>, settings: CItemSettings) =
        settings.tooltip.forEach { list.add(it) }
}
