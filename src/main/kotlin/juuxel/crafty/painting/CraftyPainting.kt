/* This file is a part of the Crafty project
 * by Juuxel, licensed under the GPLv3 license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.painting

import juuxel.crafty.util.Content
import net.minecraft.entity.decoration.painting.PaintingMotive

class CraftyPainting : Content<PaintingMotive> {
    var width: Int = -1
        private set

    var height: Int = -1
        private set

    override fun toMc() = PaintingMotive(width, height)
}
