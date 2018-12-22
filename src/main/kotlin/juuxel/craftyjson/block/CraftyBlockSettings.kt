/* This file is a part of the CraftyJSON project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/CraftyJSON
 */
package juuxel.craftyjson.block

import juuxel.craftyjson.util.CraftyContent
import juuxel.craftyjson.item.CraftyItemSettings
import juuxel.craftyjson.item.CraftyItemStack
import net.fabricmc.fabric.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class CraftyBlockSettings : CraftyContent<Block.Settings>() {
    lateinit var material: CraftyBlockMaterial
        private set

    var item: CraftyItemSettings? = null
        private set

    var shape: Array<CraftyBlockBounds> = arrayOf(CraftyBlockBounds().apply {
        from = doubleArrayOf(0.0, 0.0, 0.0)
        to = doubleArrayOf(16.0, 16.0, 16.0)
    })
        private set

    var drops: Array<CraftyItemStack> = emptyArray()
        private set

    override fun toMc(): Block.Settings =
        FabricBlockSettings.copy(Registry.BLOCK[Identifier(material.base)]).apply {
            if (material.lightLevel != -1)
                lightLevel(material.lightLevel)

            material.collidable?.let { collidable(it) }

            if (material.hardness != -1f)
                hardness(material.hardness)

            if (material.resistance != -1f)
                hardness(material.resistance)

            material.sounds?.let { sounds(it.mc) }
        }.build()
}
