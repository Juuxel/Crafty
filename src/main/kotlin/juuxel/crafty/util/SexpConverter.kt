/* This file is a part of the Crafty project
 * by Juuxel, licensed under the GPLv3 license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import de.tudresden.inf.lat.jsexp.Sexp
import de.tudresden.inf.lat.jsexp.SexpList

object SexpConverter {
    fun sexpToJson(sexp: Sexp): JsonElement {
        val (head, tail) = splitSexp(sexp)
        require(head.isAtomic) {
            "head must be atomic"
        }

        return when (val str = head.toString()) {
            "list" -> JsonArray().apply {
                tail.map(::sexpToJson).forEach(this::add)
            }

            "dict" -> JsonObject().apply {
                for (inner in tail) {
                    val (key, value) = splitSexp(inner)
                    add(key.toString(), sexpToJson(value.first()))
                }
            }

            else -> str.toJsonPrimitive()
        }
    }

    private fun splitSexp(sexp: Sexp): Pair<Sexp, Sexp> {
        val head = sexp.first()
        val tail = SexpList2()

        if (sexp.length > 1)
            sexp.drop(1).forEach(tail::add)

        return head to tail
    }

    private fun Sexp.first(): Sexp =
        if (isAtomic) this else this[0]

    private fun String.toJsonPrimitive(): JsonPrimitive {
        return when {
            startsWith("\"") -> JsonPrimitive(substring(1, lastIndex))
            else -> (toIntOrNull() ?: toLongOrNull() ?: toFloatOrNull() ?: toDoubleOrNull())?.let {
                JsonPrimitive(it as Number)
            } ?: JsonPrimitive(this)
        }
    }

    // avoid protected constructor
    private class SexpList2 : SexpList()
}
