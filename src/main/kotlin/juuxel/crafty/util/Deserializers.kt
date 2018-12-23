/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import juuxel.crafty.block.CMaterial
import juuxel.crafty.block.Quirk as BlockQuirk2
import juuxel.crafty.block.Quirks as BlockQuirks
import juuxel.crafty.item.CItemGroup
import juuxel.crafty.item.CItemStack
import net.minecraft.text.TextComponent
import java.lang.IllegalArgumentException
import juuxel.crafty.item.Quirk as ItemQuirk2
import juuxel.crafty.item.Quirks as ItemQuirks
import java.lang.reflect.Type

object Deserializers {
    object BlockQuirk : JsonDeserializer<BlockQuirk2> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = BlockQuirks.fromString(json.asString)
    }

    object CreativeTab : JsonDeserializer<CItemGroup> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = CItemGroup.fromString(json.asString)
    }

    object SoundGroup : JsonDeserializer<CMaterial.SoundGroup> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = CMaterial.SoundGroup.fromString(json.asString)
    }

    object ItemQuirk : JsonDeserializer<ItemQuirk2> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = ItemQuirks.fromString(json.asString)
    }

    object Size : JsonDeserializer<CItemStack.Size> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = when {
            json.isJsonPrimitive -> CItemStack.Size().apply {
                from = json.asInt
                to = json.asInt
            }

            json.isJsonObject -> CItemStack.Size().apply {
                from = json.asJsonObject["from"].asInt
                to = json.asJsonObject["to"].asInt
            }

            else -> throw IllegalArgumentException("Invalid stack size: $json")
        }
    }

    object TextComponents : JsonDeserializer<TextComponent> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = TextComponent.Serializer.fromJson(json)
    }
}
