package juuxel.crafty.util

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import blue.endless.jankson.JsonObject
import juuxel.crafty.data.Identifier

fun JsonObject.string(key: String): Either<String, String> =
    get(String::class.java, key)?.let(::Right)
        ?: Left("Missing required property: $key")

inline fun <reified T : Any> JsonObject.required(key: String): Either<String, T> =
    get(T::class.java, key)?.let(::Right)
        ?: Left("Missing required property: $key")

inline fun <reified T : Any> JsonObject.optional(key: String): T? =
    get(T::class.java, key)

fun JsonObject.id(key: String): Either<String, Identifier> =
    get(String::class.java, key)?.let(Identifier.Companion::parse)
        ?: Left("Missing required property: $key")

