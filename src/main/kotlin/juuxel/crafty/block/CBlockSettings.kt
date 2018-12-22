/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import juuxel.crafty.util.Content
import juuxel.crafty.item.CItemSettings
import juuxel.crafty.item.CItemStack
import net.fabricmc.fabric.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class CBlockSettings : Content<Block.Settings>() {
    lateinit var material: CMaterial
        private set

    var item: CItemSettings? = null
        private set

    var shape: Array<Bounds> = arrayOf(Bounds().apply {
        from = doubleArrayOf(0.0, 0.0, 0.0)
        to = doubleArrayOf(16.0, 16.0, 16.0)
    })
        private set

    var drops: Array<CItemStack> = emptyArray()
        private set

    var quirk: Quirk = Quirks.None
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
