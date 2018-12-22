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
        ifPresent("virtuoel.towelette.api.Fluidloggable") {
            logger.info("[Crafty/Compat] Loading Towelette compat")
            Quirks.register(Identifier("craftycompat:fluidloggable"), CraftyFluidloggableBlock)
        }
    }

    private inline fun ifPresent(c: String, block: () -> Unit) {
        try {
            Class.forName(c)
            block()
        } catch (e: ClassNotFoundException) {}
    }
}
