package juuxel.crafty.data

import arrow.core.Either
import arrow.core.Left
import arrow.core.identity
import arrow.core.right

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

    companion object {
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
         * Returns null if the input is invalid.
         */
        fun parse(combined: String): Either<String, Identifier> =
            if (combined.count { it == ':' } != 1) Left("Identifiers must have exactly one colon")
            else {
                val split = combined.split(':')
                Identifier(split[0], split[1]).right()
            }

        /**
         * Creates an Identifier from the `minecraft` namespace and the [path].
         */
        fun mc(path: String) = Identifier("minecraft", path)
    }
}
