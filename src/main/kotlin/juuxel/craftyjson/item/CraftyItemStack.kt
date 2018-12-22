/* This file is a part of the CraftyJSON project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/CraftyJSON
 */
package juuxel.craftyjson.item

import com.google.gson.JsonObject
import juuxel.craftyjson.CraftyContent
import juuxel.craftyjson.util.Converter
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class CraftyItemStack : CraftyContent<ItemStack>() {
    var amount: Int = 1
        private set

    var tag: JsonObject = JsonObject()
        private set

    override fun toMc(): ItemStack = ItemStack(Registry.ITEM.get(Identifier(id)), amount).also {
        it.tag = Converter.toNbt(tag)
    }
}
