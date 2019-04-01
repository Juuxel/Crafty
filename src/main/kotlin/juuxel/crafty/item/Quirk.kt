/* This file is a part of the Crafty project
 * by Juuxel, licensed under the GPLv3 license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import net.minecraft.item.Item

interface Quirk {
    val factory: (CItemSettings) -> Item
}
