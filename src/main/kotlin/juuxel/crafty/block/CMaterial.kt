/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import net.minecraft.sound.BlockSoundGroup

class CMaterial {
    lateinit var base: String
        private set

    var lightLevel: Int = -1
        private set

    var collidable: Boolean? = null
        private set

    var hardness: Float = -1f
        private set

    var resistance: Float = -1f
        private set

    var sounds: BlockSoundGroup? = null
        private set

    var redstonePower: Int = 0
        private set
}
 