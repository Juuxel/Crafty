package juuxel.crafty.data

import arrow.core.Either
import arrow.core.extensions.either.monad.binding
import blue.endless.jankson.JsonObject
import juuxel.crafty.util.JsonDeserializer
import juuxel.crafty.util.optional

data class ItemData(val group: String? = null) {
    companion object : JsonDeserializer<ItemData> {
        override fun deserialize(value: JsonObject): Either<String, ItemData> = binding {
            ItemData(value.optional("group"))
        }
    }
}
