/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import juuxel.crafty.util.Content
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup

class CItemGroup : Content<ItemGroup>() {
    lateinit var iconItem: CItemStack private set

    override fun toMc() = FabricItemGroupBuilder.build(id, iconItem::toMc)
}
