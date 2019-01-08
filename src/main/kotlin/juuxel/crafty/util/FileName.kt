/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import java.io.File

/**
 * A file name.
 *
 * @property name the name without the extension
 * @property extension the extension without the dot
 * @property fullPath the full file path
 */
data class FileName(val name: String, val extension: String, val fullPath: String) {
    companion object {
        fun fromFile(file: File) = FileName(file.nameWithoutExtension, file.extension, file.path)
    }
}
