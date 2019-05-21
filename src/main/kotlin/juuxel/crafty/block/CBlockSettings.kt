/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.JsonAdapter
import juuxel.crafty.util.Content
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderLayer
import net.minecraft.sound.BlockSoundGroup
import org.apache.logging.log4j.LogManager
import java.lang.reflect.Type

@JsonAdapter(CBlockSettings.Companion::class)
sealed class CBlockSettings : Content<Block.Settings> {
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

    class Copied : CBlockSettings()
    class FromMaterial : CBlockSettings()

    private class Builder {
        private var lightLevel: Int = -1
        private var collidable: Boolean? = null
        private var hardness: Float = -1f
        private var resistance: Float = -1f
        private var sounds: BlockSoundGroup? = null

        fun lightLevel(value: Int) { lightLevel = value }
        fun collidable(value: Boolean) { collidable = value }
        fun hardness(value: Float) { hardness = value }
        fun resistance(value: Float) { resistance = value }
        fun sounds(value: BlockSoundGroup) { sounds = value }

        fun build(ctor: (Int, Boolean, Float, Float, BlockSoundGroup?) -> CBlockSettings) =
            ctor(lightLevel, collidable ?: false, hardness, resistance, sounds)
    }

    companion object : JsonDeserializer<CBlockSettings> {
        private val LOGGER = LogManager.getLogger()

        override fun deserialize(json: JsonElement, type: Type?, context: JsonDeserializationContext) =
            json.run {
                require(this.isJsonObject) { "Block attributes must be a JSON object" }
                this as JsonObject
                val attributes = when {
                    has("copyFrom") -> Copied()
                    has("material") -> FromMaterial()
                    else -> throw IllegalArgumentException("Block attributes must have either copyFrom or material")
                }

                attributes.collidable =

                attributes
            }
    }
}
