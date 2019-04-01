/* This file is a part of the Crafty project
 * by Juuxel, licensed under the GPLv3 license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import com.google.gson.Gson
import com.google.gson.stream.JsonReader

inline fun <reified T> Gson.fromJson(reader: JsonReader): T =
    fromJson<T>(reader, T::class.java)
 