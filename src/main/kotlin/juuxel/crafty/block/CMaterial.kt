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

    var sounds: SoundGroup? = null
        private set

    enum class SoundGroup(val mc: BlockSoundGroup) {
        Wood(BlockSoundGroup.WOOD),
        Gravel(BlockSoundGroup.GRAVEL),
        Grass(BlockSoundGroup.GRASS),
        Stone(BlockSoundGroup.STONE),
        Metal(BlockSoundGroup.METAL),
        Glass(BlockSoundGroup.GLASS),
        Wool(BlockSoundGroup.WOOL),
        Sand(BlockSoundGroup.SAND),
        Snow(BlockSoundGroup.SNOW),
        Ladder(BlockSoundGroup.LADDER),
        Anvil(BlockSoundGroup.ANVIL),
        Slime(BlockSoundGroup.SLIME),
        WetGrass(BlockSoundGroup.WET_GRASS),
        Coral(BlockSoundGroup.CORAL),
        Bamboo(BlockSoundGroup.BAMBOO),
        BambooSapling(BlockSoundGroup.BAMBOO_SAPLING),
        Scaffolding(BlockSoundGroup.SCAFFOLDING)
    }
}
 