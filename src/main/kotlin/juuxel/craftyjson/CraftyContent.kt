/* This file is a part of the CraftyJSON project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/CraftyJSON
 */
package juuxel.craftyjson

abstract class CraftyContent<out T> {
    lateinit var id: String
        private set

    abstract fun toMc(): T
}