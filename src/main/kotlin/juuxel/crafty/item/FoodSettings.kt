/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import juuxel.crafty.util.WorldEvent
import net.minecraft.entity.effect.StatusEffectInstance

class FoodSettings {
    var hungerRestored: Int = 0
        private set

    var saturation: Float = 0f
        private set

    var wolfFood: Boolean = false
        private set

    var effect: StatusEffectInstance? = null
        private set

    var effectChance: Float = 1f
        private set

    var onConsume: WorldEvent? = null
        private set
}
