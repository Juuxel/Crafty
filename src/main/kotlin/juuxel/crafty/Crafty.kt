/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty

import arrow.effects.IO
import blue.endless.jankson.Jankson
import juuxel.crafty.data.BlockData
import juuxel.crafty.data.Identifier
import juuxel.crafty.data.ItemData
import juuxel.crafty.impl.fabric.CraftyBlock
import juuxel.crafty.loading.Module
import juuxel.crafty.loading.PackLoader
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.registry.Registry
import java.nio.file.Files
import java.nio.file.Path

object Crafty : ModInitializer {
    override fun onInitialize() {
        PackLoader(setOf(BlockModule, ItemModule)).load(
            FabricLoader.getInstance().gameDirectory.toPath()
        ).unsafeRunSync()
    }

    private fun Identifier.toMc() = net.minecraft.util.Identifier(namespace, path)

    private fun readItemSettings(data: ItemData): Item.Settings =
        Item.Settings().apply {
            if (data.group != null) {
                group(ItemGroup.GROUPS.find { it.name == data.group })
            }
        }

    private object BlockModule : Module {
        override val name = "blocks"

        override fun load(path: Path, id: Identifier, jankson: Jankson) = IO<Unit> {
            val data = jankson.fromJson(jankson.load(Files.newInputStream(path)), BlockData::class.java)
            val block = CraftyBlock(Block.Settings.copy(Registry.BLOCK[data.settings.base.toMc()]), data)
            val item = data.item?.let { BlockItem(block, readItemSettings(it)) }

            Registry.register(Registry.BLOCK, id.toMc(), block)
            if (item != null) {
                Registry.register(Registry.ITEM, id.toMc(), item)
            }
        }
    }

    private object ItemModule : Module {
        override val name = "items"

        override fun load(path: Path, id: Identifier, jankson: Jankson) = IO<Unit> {
            val data = jankson.fromJson(jankson.load(Files.newInputStream(path)), ItemData::class.java)
            val item = Item(readItemSettings(data))

            Registry.register(Registry.ITEM, id.toMc(), item)
        }
    }
}
