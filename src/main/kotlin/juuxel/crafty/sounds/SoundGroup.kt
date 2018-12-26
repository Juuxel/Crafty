/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.sounds

import juuxel.crafty.util.Content
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.BlockSoundGroup.*
import net.minecraft.sound.SoundEvent
import net.minecraft.util.registry.DefaultMappedRegistry

class SoundGroup : Content<BlockSoundGroup> {
    var volume: Float = 1f
        private set

    var pitch: Float = 1f
        private set

    lateinit var breakSound: SoundEvent private set
    lateinit var stepSound: SoundEvent private set
    lateinit var placeSound: SoundEvent private set
    lateinit var hitSound: SoundEvent private set
    lateinit var fallSound: SoundEvent private set

    override fun toMc() = BlockSoundGroup(volume, pitch, breakSound, stepSound, placeSound, hitSound, fallSound)

    companion object : DefaultMappedRegistry<BlockSoundGroup>("stone") {
        init {
            register(this, "minecraft:wood", WOOD)
            register(this, "minecraft:gravel", GRAVEL)
            register(this, "minecraft:grass", GRASS)
            register(this, "minecraft:stone", STONE)
            register(this, "minecraft:metal", METAL)
            register(this, "minecraft:glass", GLASS)
            register(this, "minecraft:wool", WOOL)
            register(this, "minecraft:sand", SAND)
            register(this, "minecraft:snow", SNOW)
            register(this, "minecraft:ladder", LADDER)
            register(this, "minecraft:anvil", ANVIL)
            register(this, "minecraft:slime", SLIME)
            register(this, "minecraft:wet_grass", WET_GRASS)
            register(this, "minecraft:coral", CORAL)
            register(this, "minecraft:bamboo", BAMBOO)
            register(this, "minecraft:bamboo_sapling", BAMBOO_SAPLING)
            register(this, "minecraft:scaffolding", SCAFFOLDING)
        }
    }
}
