/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.loading

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.core.extensions.either.monad.binding
import blue.endless.jankson.JsonObject
import blue.endless.jankson.JsonPrimitive
import juuxel.crafty.util.JsonDeserializer

// TODO: Is the id needed?
data class PackMetadata(val id: String, val name: String?) {
    object Deserializer : JsonDeserializer<PackMetadata> {
        override fun deserialize(value: JsonObject): Either<String, PackMetadata> {
            val packVersion = value["pack_version"] ?: return Left("Missing pack version!")

            if ((packVersion as? JsonPrimitive)?.value != CURRENT_FORMAT_VERSION) {
                return Left("Unsupported format: $packVersion")
            }

            fun string(key: String): Either<String, String> =
                value.get(String::class.java, key)?.let(::Right)
                        ?: Left("Missing required property: $key")

            return binding {
                val id = string("id").bind()
                val name = string("name").fold({ id }, { it })
                PackMetadata(id, name)
            }
        }
    }

    companion object {
        private const val CURRENT_FORMAT_VERSION = 1
    }
}
