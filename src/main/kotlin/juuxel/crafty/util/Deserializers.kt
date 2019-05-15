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
import net.minecraft.block.BlockRenderLayer
import net.minecraft.command.arguments.BlockStateArgument
import net.minecraft.command.arguments.BlockStateArgumentType
import net.minecraft.datafixers.NbtOps
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.ItemGroup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.lang.IllegalArgumentException
import juuxel.crafty.item.Quirk as ItemQuirk
import juuxel.crafty.item.Quirks as ItemQuirks
import java.lang.reflect.Type
import java.util.Locale

object Deserializers {
    fun applyToGson(builder: GsonBuilder): Unit = builder.run {
        registerTypeAdapter(BlockQuirk::class.java, DBlockQuirk)
        registerTypeAdapter(ItemQuirk::class.java, DItemQuirk)
        registerTypeAdapter(ItemGroup::class.java, ItemGroups)
        registerTypeAdapter(BlockSoundGroup::class.java, SoundGroups)
        registerTypeAdapter(CItemStack.Size::class.java, StackSize)
        registerTypeAdapter(Component::class.java, TextComponents)
        registerTypeAdapter(StatusEffectInstance::class.java, StatusEffects)
        registerTypeAdapter(SoundEvent::class.java, SoundEvents)
        registerTypeAdapter(Identifier::class.java, Identifiers)
        registerTypeAdapter(BlockStateArgument::class.java, BlockStateArguments)
        registerTypeAdapter(BlockRenderLayer::class.java, BlockRenderLayers)
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

    object StackSize : JsonDeserializer<CItemStack.Size> {
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

    object TextComponents : JsonDeserializer<Component> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = Component.Serializer.fromJson(json)
    }

    object StatusEffects : JsonDeserializer<StatusEffectInstance> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ) = StatusEffectInstance.deserialize(Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, json) as CompoundTag)
    }

    object SoundEvents : JsonDeserializer<SoundEvent> {
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?) =
            Registry.SOUND_EVENT.get(Identifier(json.asString))
    }

    object Identifiers : JsonDeserializer<Identifier> {
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?) =
            Identifier(json.asString)
    }

    object BlockStateArguments : JsonDeserializer<BlockStateArgument> {
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?) =
            BlockStateArgumentType.create().method_9654(StringReader(json.asString))
    }

    object BlockRenderLayers : JsonDeserializer<BlockRenderLayer> {
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?) =
            BlockRenderLayer.valueOf(json.asString.toUpperCase(Locale.ROOT))
    }
}
