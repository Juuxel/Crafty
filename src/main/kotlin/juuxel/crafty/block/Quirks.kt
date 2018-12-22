/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import net.minecraft.block.Block
import org.apache.logging.log4j.LogManager
import java.util.*

enum class Quirks(override val factory: (CBlockSettings) -> Block) : Quirk {
    None(::CraftyBlock), Waterloggable(::CraftyWaterloggableBlock),
    Falling(::CraftyFallingBlock);

    companion object {
        private val logger = LogManager.getLogger()
        private val stringMap: MutableMap<String, Quirk> = mutableMapOf(
            "none" to None,
            "waterloggable" to Waterloggable,
            "falling" to Falling
        )

        fun register(name: String, quirk: Quirk) {
            logger.info("[Crafty] Registering quirk $name: $quirk")

            if (!stringMap.containsKey(name))
                stringMap[name] = quirk
            else
                logger.warn("[Crafty] Trying to register quirk $quirk, name $name already registered")
        }

        fun fromString(str: String) =
            stringMap[str.toLowerCase(Locale.ROOT)] ?: throw IllegalArgumentException("Quirk $str not found")
    }
}
