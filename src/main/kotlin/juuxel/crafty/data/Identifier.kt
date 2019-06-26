package juuxel.crafty.data

import arrow.core.*
import juuxel.crafty.util.JsonPrimitiveDeserializer

// Copied from JSON Factory

/**
 * An identifier with a [namespace] and a [path].
 */
data class Identifier(val namespace: String, val path: String) {
    override fun toString() = "$namespace:$path"

    /**
     * Returns a new `Identifier` with its path prefixed with the [prefix].
     */
    fun prefixPath(prefix: String): Identifier = copy(path = prefix + path)

    /**
     * Returns a new `Identifier` with its path suffixed with the [suffix].
     */
    fun suffixPath(suffix: String): Identifier = copy(path = path + suffix)

    /**
     * Returns a new `Identifier` with its path wrapped with the [prefix] and the [suffix].
     */
    fun wrapPath(prefix: String, suffix: String): Identifier = copy(path = "$prefix$path$suffix")

    /**
     * Returns `true` if this identifier is a valid Minecraft identifier.
     */
    fun isValidMc(): Boolean = namespace.matches(NAMESPACE_REGEX) && path.matches(PATH_REGEX)

    private fun validate(): Option<Identifier> =
        if (isValidMc()) this.some()
        else None

    companion object : JsonPrimitiveDeserializer<Identifier> {
        private val NAMESPACE_REGEX = "[a-z0-9_.-]+".toRegex()
        private val PATH_REGEX = "[a-z0-9/._-]+".toRegex()

        // TODO: Upstream parsing improvements
        /**
         * Creates an Identifier from a [combined] string in the `namespace:path` format.
         *
         * @throws IllegalArgumentException if the input is invalid
         */
        operator fun invoke(combined: String): Identifier =
            parse(combined).fold(
                { throw IllegalArgumentException(it) },
                ::identity
            )

        /**
         * Creates an Identifier from a [combined] string in the `namespace:path` format.
         * Returns null if the input is invalid.
         */
        fun orNull(combined: String): Identifier? =
            parse(combined).fold({ null }, ::identity)

        /**
         * Creates an Identifier from a [combined] string in the `namespace:path` format.
         * Returns `Either.Left` if the input is invalid.
         */
        fun parse(combined: String): Either<String, Identifier> =
            if (combined.count { it == ':' } != 1) Left("Identifiers must have exactly one colon")
            else {
                val split = combined.split(':')
                Identifier(split[0], split[1]).right()
            }

        /**
         * Creates an Identifier from a [combined] string in the `namespace:path` format,
         * using Minecraft rules (implicit `minecraft` namespace, character validation).
         * Returns `Either.Left` if the input is invalid.
         */
        fun parseMc(combined: String): Either<String, Identifier> = when (combined.count { it == ':' }) {
            0 -> mc(combined).validate().toEither { "Path contains invalid characters" }
            1 -> parse(combined).flatMap { it.validate().toEither { "Identifier contains invalid characters" } }
            else -> Left("An identifier must have exactly one colon (':')")
        }

        /**
         * Creates an Identifier from the `minecraft` namespace and the [path].
         */
        fun mc(path: String) = Identifier("minecraft", path)

        override fun deserialize(value: Any?) = parseMc(value.toString())
    }
}
