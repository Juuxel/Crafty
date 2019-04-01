/* This file is a part of the Crafty project
 * by Juuxel, licensed under the GPLv3 license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import juuxel.crafty.util.Content
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier

class CItemGroup : Content<(Identifier) -> ItemGroup> {
    lateinit var iconItem: CItemStack private set

    override fun toMc() = { id: Identifier -> FabricItemGroupBuilder.build(id, iconItem::toMc) }
}
