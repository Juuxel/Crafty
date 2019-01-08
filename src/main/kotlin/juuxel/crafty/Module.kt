/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty

import juuxel.crafty.util.FileName
import java.io.Reader

interface Module {
    val name: String

    fun loadContent(contentPack: String, reader: Reader, fileName: FileName)
}
