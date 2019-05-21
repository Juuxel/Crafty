/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.compat

import juuxel.crafty.block.Quirks
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager

object CompatLoader {
    private val logger = LogManager.getLogger()

    fun load() {
        if (FabricLoader.getInstance().isModLoaded("towelette")) {
            logger.info("[Crafty/Compat] Loading Towelette compat")
            Quirks.add(Identifier("towelette", "fluidloggable"), ToweletteFluidloggableBlock)
        }
    }
}
