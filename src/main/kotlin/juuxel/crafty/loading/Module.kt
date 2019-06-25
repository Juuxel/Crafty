package juuxel.crafty.loading

import arrow.effects.IO
import java.nio.file.Path

interface Module {
    val name: String

    fun load(path: Path): IO<Unit>
}
