/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.compat

import juuxel.crafty.Crafty
import juuxel.crafty.block.Quirks
import juuxel.crafty.painting.PaintingModule
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager

object CompatLoader {
    private val logger = LogManager.getLogger()

    fun load() {
        ifPresent("virtuoel.towelette.api.Fluidloggable") {
            logger.info("[Crafty/Compat] Loading Towelette compat")
            Quirks.add(Identifier("towelette:fluidloggable"), ToweletteFluidloggableBlock)
        }
    }

    private inline fun ifPresent(c: String, block: () -> Unit) {
        try {
            Class.forName(c)
            block()
        } catch (e: ClassNotFoundException) {}
    }
}
