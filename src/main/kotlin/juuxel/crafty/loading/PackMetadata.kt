/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.loading

import arrow.core.Either
import arrow.core.Left
import arrow.core.extensions.either.monad.binding
import blue.endless.jankson.JsonObject
import blue.endless.jankson.JsonPrimitive
import juuxel.crafty.util.JsonDeserializer
import juuxel.crafty.util.string

// TODO: Is the id needed?
data class PackMetadata(val id: String, val name: String?) {
    companion object : JsonDeserializer<PackMetadata> {
        private const val CURRENT_FORMAT_VERSION = 1

        override fun deserialize(value: JsonObject): Either<String, PackMetadata> {
            val packFormat = value["pack_format"] ?: return Left("Missing pack format!")

            if ((packFormat as? JsonPrimitive)?.value != CURRENT_FORMAT_VERSION) {
                return Left("Unsupported format: $packFormat")
            }

            return binding {
                val id = value.string("id").bind()
                val name = value.string("name").fold({ id }, { it })
                PackMetadata(id, name)
            }
        }
    }
}
