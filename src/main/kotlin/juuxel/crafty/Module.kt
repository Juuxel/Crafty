/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty

import java.nio.file.Path

interface Module {
    val name: String

    fun loadContent(contentPack: String, path: Path)
}
