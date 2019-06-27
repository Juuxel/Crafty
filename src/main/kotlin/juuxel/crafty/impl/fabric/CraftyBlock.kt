package juuxel.crafty.impl.fabric

import juuxel.crafty.data.BlockData
import juuxel.crafty.data.Shape
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.EntityContext
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView

class CraftyBlock(settings: Settings, data: BlockData) : Block(settings) {
    private val outlineShape = data.outlineShape.toMc()
    private val collisionShape = data.collisionShape.toMc()

    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: EntityContext?
    ) = outlineShape

    override fun getCollisionShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: EntityContext?
    ) = collisionShape

    companion object {
        private fun Shape.toMc(): VoxelShape =
            createCuboidShape(from[0], from[1], from[2], to[0], to[1], to[2])
    }
}
