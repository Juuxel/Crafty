/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import juuxel.crafty.block.Quirk as Quirk2
import juuxel.crafty.block.Quirks
import juuxel.crafty.item.CItemGroup
import java.lang.reflect.Type

object Deserializers {
    object Quirk : JsonDeserializer<Quirk2> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = Quirks.fromString(json.asString)
    }

    object CreativeTab : JsonDeserializer<CItemGroup> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = CItemGroup.fromString(json.asString)
    }
}
