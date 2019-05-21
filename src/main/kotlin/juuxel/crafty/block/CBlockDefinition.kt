/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import juuxel.crafty.util.Content
import juuxel.crafty.item.CItemSettings
import juuxel.crafty.item.CItemStack
import juuxel.crafty.util.WorldEvent
import net.fabricmc.fabric.api.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderLayer
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.shape.VoxelShape

class CBlockDefinition : Content<Block.Settings> {
    lateinit var settings: CBlockSettings
        private set

    var item: CItemSettings? = CItemSettings()
        private set

    var outlineShape: Array<Bounds> = arrayOf(Bounds().apply {
        from = doubleArrayOf(0.0, 0.0, 0.0)
        to = doubleArrayOf(16.0, 16.0, 16.0)
    })
        private set

    var collisionShape: Array<Bounds>? = null
        private set

    var drops: Array<CItemStack> = emptyArray()
        private set

    var quirk: Quirk = Quirks.None
        private set

    var onActivate: WorldEvent.OnBlockActivation? = null
        private set

    var onPlaced: WorldEvent? = null
        private set

    var onBreak: WorldEvent? = null
        private set

    var falling: FallingBlockSettings? = null
        private set

    val builtOutlineShape: VoxelShape by lazy {
        BlockUtils.buildShape(outlineShape)
    }

    val builtCollisionShape: VoxelShape? by lazy {
        collisionShape?.let(BlockUtils::buildShape)
    }

    var redstonePower: Int = 0
        private set

    var renderLayer: BlockRenderLayer = BlockRenderLayer.SOLID
        private set

    override fun toMc(): Block.Settings =
        FabricBlockSettings.copy(Registry.BLOCK[Identifier(settings.base)]).apply {
            if (settings.lightLevel != -1)
                lightLevel(settings.lightLevel)

            settings.collidable?.let { collidable(it) }

            if (settings.hardness != -1f)
                hardness(settings.hardness)

            if (settings.resistance != -1f)
                hardness(settings.resistance)

            settings.sounds?.let { sounds(it) }
        }.build()
}
