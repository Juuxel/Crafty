/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

abstract class CraftyContent<out T> {
    lateinit var id: String
        private set

    abstract fun toMc(): T
}
