/* This file is a part of the CraftyJSON project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/CraftyJSON
 */
package juuxel.craftyjson.util

import com.google.gson.JsonObject
import com.mojang.datafixers.Dynamic
import com.mojang.datafixers.types.JsonOps
import net.minecraft.datafixers.NbtOps
import net.minecraft.nbt.CompoundTag

object Converter {
    fun toNbt(json: JsonObject): CompoundTag {
        return Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, json).run {
            this as? CompoundTag ?: throw IllegalArgumentException()
        }
    }
}
