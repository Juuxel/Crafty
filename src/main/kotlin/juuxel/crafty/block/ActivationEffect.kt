/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 */
package juuxel.crafty.block

import juuxel.crafty.item.CItemStack

class ActivationEffect {
    var destroy: Boolean = false
        private set

    var spawnItems: Array<CItemStack> = emptyArray()
        private set

    companion object {
        val default = ActivationEffect()
    }
}
