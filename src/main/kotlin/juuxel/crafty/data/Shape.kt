package juuxel.crafty.data

import arrow.core.Either
import arrow.core.extensions.either.monad.binding
import blue.endless.jankson.JsonObject
import juuxel.crafty.util.JsonDeserializer
import juuxel.crafty.util.required

/**
 * A block shape in the model element shape format (coords between 0 and 16).
 */
data class Shape(val from: List<Double>, val to: List<Double>) {
    init {
        require(from.size == 3 && to.size == 3) {
            "The input lists must have exactly 3 elements"
        }
    }

    companion object : JsonDeserializer<Shape> {
        override fun deserialize(value: JsonObject): Either<String, Shape> = binding {
            Shape(
                value.required<DoubleArray>("from").bind().toList(),
                value.required<DoubleArray>("to").bind().toList()
            )
        }
    }
}
