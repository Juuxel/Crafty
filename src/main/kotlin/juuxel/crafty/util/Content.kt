/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import net.minecraft.util.Identifier

abstract class Content<out T> {
    lateinit var id: Identifier
        private set

    abstract fun toMc(): T
}
