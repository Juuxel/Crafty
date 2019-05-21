/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.block

import net.minecraft.block.Block

interface Quirk {
    val factory: (CBlockDefinition) -> Block
}
