package juuxel.crafty.data

import arrow.core.Either
import arrow.core.extensions.either.monad.binding
import blue.endless.jankson.JsonObject
import juuxel.crafty.util.JsonDeserializer
import juuxel.crafty.util.id
import juuxel.crafty.util.optional
import juuxel.crafty.util.required

data class BlockData(
    val settings: Settings,
    val outlineShape: Shape = Shape(listOf(0.0, 0.0, 0.0), listOf(16.0, 16.0, 16.0)),
    val collisionShape: Shape = outlineShape,
    val item: ItemData? = ItemData()
) {
    data class Settings(
        val base: Identifier
    ) {
        companion object : JsonDeserializer<Settings> {
            override fun deserialize(value: JsonObject): Either<String, Settings> = binding {
                Settings(value.id("base").bind())
            }
        }
    }

    companion object : JsonDeserializer<BlockData> {
        override fun deserialize(value: JsonObject): Either<String, BlockData> = binding {
            val settings = value.required<Settings>("settings").bind()

            var result = BlockData(settings)

            value.optional<Shape>("outline_shape")?.let { result = result.copy(outlineShape = it) }
            value.optional<Shape>("collision_shape")?.let { result = result.copy(collisionShape = it) }
            if (value.containsKey("item")) {
                result = result.copy(item = value.optional<ItemData>("item"))
            }

            result
        }
    }
}
