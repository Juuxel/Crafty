/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.item

import juuxel.crafty.util.Content
import juuxel.crafty.util.WorldEvent
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.FoodItemSetting

class FoodSettings : Content<FoodItemSetting> {
    var hungerRestored: Int = 0
        private set

    var saturation: Float = 0f
        private set

    var wolfFood: Boolean = false
        private set

    var effectChances: List<StatusEffectChance>? = null
        private set

    var onConsume: WorldEvent? = null
        private set

    var alwaysEdible: Boolean = false
        private set

    var eatenFast: Boolean = false
        private set

    override fun toMc() = FoodItemSetting.Builder()
        .run {
            hunger(hungerRestored)
            saturationModifier(saturation)
            if (wolfFood) wolfFood()
            if (alwaysEdible) alwaysEdible()
            if (eatenFast) eatenFast()
            effectChances?.forEach { (effect, chance) ->
                statusEffect(effect, chance)
            }

            build()
        }

    class StatusEffectChance {
        lateinit var effect: StatusEffectInstance
            private set
        var chance: Float = 0f
            private set

        operator fun component1(): StatusEffectInstance = effect
        operator fun component2(): Float = chance
    }
}
