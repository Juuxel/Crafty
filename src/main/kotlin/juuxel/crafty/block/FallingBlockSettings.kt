/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import juuxel.crafty.util.WorldEvent
import net.minecraft.command.arguments.BlockStateArgument

class FallingBlockSettings {
    var hurtsEntities = false
        private set

    var breakingState: BlockStateArgument? = null
        private set

    var breakingChance: Double = 0.2
        private set

    var onLanding: WorldEvent? = null
        private set

    var onBreaking: WorldEvent? = null
        private set
}
