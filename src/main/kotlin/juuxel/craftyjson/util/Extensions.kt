/* This file is a part of the CraftyJSON project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/CraftyJSON
 */
package juuxel.craftyjson.util

import com.google.gson.Gson
import com.google.gson.stream.JsonReader

inline fun <reified T> Gson.fromJson(reader: JsonReader): T =
    fromJson<T>(reader, T::class.java)
 