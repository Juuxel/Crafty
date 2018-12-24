/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.painting

import juuxel.crafty.util.Content
import juuxel.palette.api.PaletteMotive
import net.minecraft.util.Identifier

class CraftyPainting : Content<PaletteMotive>() {
    lateinit var texture: Identifier
        private set

    var width: Int = -1
        private set

    var height: Int = -1
        private set

    var textureX: Int = -1
        private set

    var textureY: Int = -1
        private set

    override fun toMc() = PaletteMotive(width, height, textureX, textureY, texture)
}
