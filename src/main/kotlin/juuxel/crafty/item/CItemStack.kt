/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import com.google.gson.JsonObject
import com.mojang.datafixers.Dynamic
import com.mojang.datafixers.types.JsonOps
import juuxel.crafty.util.Content
import net.minecraft.datafixers.NbtOps
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.registry.Registry
import java.util.concurrent.ThreadLocalRandom

class CItemStack : Content<ItemStack>() {
    var amount: Size = Size()
        private set

    var tag: JsonObject = JsonObject()
        private set

    override fun toMc(): ItemStack = ItemStack(Registry.ITEM.get(id), amount.toInt()).also {
        it.tag = Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, tag) as CompoundTag
    }

    class Size {
        var from: Int = 1
            internal set

        var to: Int = 1
            internal set

        fun toInt() =
            if (from == to) from
            else ThreadLocalRandom.current().nextInt(from, to + 1)
    }
}
