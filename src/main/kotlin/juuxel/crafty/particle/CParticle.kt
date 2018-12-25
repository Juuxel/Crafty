/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty.particle

class CParticle {
    /**
     * Uses the `/particle` format.
     */
    lateinit var particle: String private set

    var xOffset: Double = 0.0
        private set
    var yOffset: Double = 0.0
        private set
    var zOffset: Double = 0.0
        private set

    var velocityX: Double = 0.0
        private set
    var velocityY: Double = 0.0
        private set
    var velocityZ: Double = 0.0
        private set
}
