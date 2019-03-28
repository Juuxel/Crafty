/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mojang.brigadier.StringReader
import com.mojang.datafixers.Dynamic
import com.mojang.datafixers.types.JsonOps
import juuxel.crafty.block.Quirk as BlockQuirk
import juuxel.crafty.block.Quirks as BlockQuirks
import juuxel.crafty.item.CItemStack
import juuxel.crafty.sounds.SoundGroup
import net.minecraft.command.arguments.BlockArgument
import net.minecraft.command.arguments.BlockArgumentType
import net.minecraft.datafixers.NbtOps
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.ItemGroup
import net.minecraft.nbt.CompoundTag
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundEvent
import net.minecraft.text.TextComponent
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.lang.IllegalArgumentException
import juuxel.crafty.item.Quirk as ItemQuirk
import juuxel.crafty.item.Quirks as ItemQuirks
import java.lang.reflect.Type

object Deserializers {
    fun applyToGson(builder: GsonBuilder): Unit = builder.run {
        registerTypeAdapter(BlockQuirk::class.java, DBlockQuirk)
        registerTypeAdapter(ItemQuirk::class.java, DItemQuirk)
        registerTypeAdapter(ItemGroup::class.java, ItemGroups)
        registerTypeAdapter(BlockSoundGroup::class.java, SoundGroups)
        registerTypeAdapter(CItemStack.Size::class.java, Size)
        registerTypeAdapter(TextComponent::class.java, TextComponents)
        registerTypeAdapter(StatusEffectInstance::class.java, StatusEffect)
        registerTypeAdapter(SoundEvent::class.java, Sound)
        registerTypeAdapter(Identifier::class.java, Id)
        registerTypeAdapter(BlockArgument::class.java, BlockArguments)
    }

    object DBlockQuirk : JsonDeserializer<BlockQuirk> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = BlockQuirks.fromString(json.asString)
    }

    object ItemGroups : JsonDeserializer<ItemGroup> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = ItemGroup.GROUPS.first { it.name == json.asString }
    }

    object SoundGroups : JsonDeserializer<BlockSoundGroup> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = SoundGroup[Identifier(json.asString)]
    }

    object DItemQuirk : JsonDeserializer<ItemQuirk> {
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

    object StatusEffect : JsonDeserializer<StatusEffectInstance> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = StatusEffectInstance.deserialize(Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, json) as CompoundTag)
    }

    object Sound : JsonDeserializer<SoundEvent> {
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?) =
            Registry.SOUND_EVENT.get(Identifier(json.asString))
    }

    object Id : JsonDeserializer<Identifier> {
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?) =
            Identifier(json.asString)
    }

    object BlockArguments : JsonDeserializer<BlockArgument> {
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?) =
            BlockArgumentType.create().method_9654(StringReader(json.asString))
    }
}
