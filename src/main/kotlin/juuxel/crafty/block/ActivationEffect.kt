/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import juuxel.crafty.util.WorldEvent

class ActivationEffect : WorldEvent() {
    var destroy: Boolean = false
        private set

    companion object {
        val default = ActivationEffect()
    }
}
