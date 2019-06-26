package juuxel.crafty.util

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import blue.endless.jankson.JsonObject
import juuxel.crafty.data.Identifier

fun JsonObject.string(key: String): Either<String, String> =  required(key)

inline fun <reified T : Any> JsonObject.required(key: String): Either<String, T> =
    runCatching { get(T::class.java, key) }
        .fold(
            onFailure = { Left(it.message ?: it::class.java.simpleName) },
            onSuccess = { if (it != null) Right(it) else Left("Missing required property: $key") }
        )

inline fun <reified T : Any> JsonObject.optional(key: String): T? =
    runCatching { get(T::class.java, key) }.getOrNull()

fun JsonObject.id(key: String): Either<String, Identifier> = required(key)
