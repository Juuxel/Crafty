package juuxel.crafty.loading

import arrow.effects.IO
import blue.endless.jankson.Jankson
import juuxel.crafty.data.Identifier
import java.nio.file.Path

interface Module {
    val name: String

    fun load(path: Path, id: Identifier, jankson: Jankson): IO<Unit>
}
