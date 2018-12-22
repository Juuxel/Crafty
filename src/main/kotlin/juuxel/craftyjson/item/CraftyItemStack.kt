/* This file is a part of the CraftyJSON project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/CraftyJSON
 */
package juuxel.craftyjson.item

import com.google.gson.JsonObject
import com.mojang.datafixers.Dynamic
import com.mojang.datafixers.types.JsonOps
import juuxel.craftyjson.util.CraftyContent
import net.minecraft.datafixers.NbtOps
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class CraftyItemStack : CraftyContent<ItemStack>() {
    var amount: Int = 1
        private set

    var tag: JsonObject = JsonObject()
        private set

    override fun toMc(): ItemStack = ItemStack(Registry.ITEM.get(Identifier(id)), amount).also {
        it.tag = Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, tag) as CompoundTag
    }
}
