/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import arrow.core.Either
import arrow.core.identity
import blue.endless.jankson.JsonObject
import org.apache.logging.log4j.LogManager
import java.util.function.Function

interface JsonDeserializer<out T> {
    fun deserialize(value: JsonObject): Either<String, T>

    fun toJanksonDeserializer(): Function<JsonObject, @UnsafeVariance T> =
        Function {
            deserialize(it).fold(
                ifLeft = { msg ->
                    val exceptionMessage = "Error while deserializing: $msg"
                    LOGGER.error("[Crafty] $exceptionMessage")
                    throw IllegalArgumentException(exceptionMessage)
                },
                ifRight = ::identity
            )
        }

    companion object {
        private val LOGGER = LogManager.getLogger()
    }
}
