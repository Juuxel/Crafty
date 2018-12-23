/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.compat

import juuxel.crafty.block.Quirks
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager

object CompatLoader {
    private val logger = LogManager.getLogger()

    fun load() {
        ifPresent("juuxel.watereddown.api.Fluidloggable") {
            logger.info("[Crafty/Compat] Loading Watered Down compat")
            Quirks.register(Identifier("watereddown:fluidloggable"), WDFluidloggableBlock)
            Quirks.register(Identifier("craftycompat:fluidloggable"), WDFluidloggableBlock)
        }

        ifPresent("virtuoel.towelette.api.Fluidloggable") {
            logger.info("[Crafty/Compat] Loading Towelette compat")
            Quirks.register(Identifier("towelette:fluidloggable"), ToweletteFluidloggableBlock)
            Quirks.register(Identifier("craftycompat:fluidloggable"), ToweletteFluidloggableBlock)
        }
    }

    private inline fun ifPresent(c: String, block: () -> Unit) {
        try {
            Class.forName(c)
            block()
        } catch (e: ClassNotFoundException) {}
    }
}
