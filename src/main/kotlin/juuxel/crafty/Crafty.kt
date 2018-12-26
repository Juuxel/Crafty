/* This file is a part of the Crafty project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/Crafty
 */
package juuxel.crafty

import com.google.gson.GsonBuilder
import juuxel.crafty.block.BlockModule
import juuxel.crafty.block.CMaterial
import juuxel.crafty.compat.CompatLoader
import juuxel.crafty.item.*
import juuxel.crafty.util.Deserializers
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.FabricLoader
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.ItemGroup
import net.minecraft.sound.SoundEvent
import net.minecraft.text.TextComponent
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import java.io.File
import java.nio.file.Files
import juuxel.crafty.block.Quirk as BlockQuirk
import juuxel.crafty.item.Quirk as ItemQuirk

object Crafty : ModInitializer {
    private val logger = LogManager.getLogger()
    internal val gson = GsonBuilder().apply {
        registerTypeAdapter(BlockQuirk::class.java, Deserializers.BlockQuirk)
        registerTypeAdapter(ItemQuirk::class.java, Deserializers.ItemQuirk)
        registerTypeAdapter(ItemGroup::class.java, Deserializers.ItemGroups)
        registerTypeAdapter(CMaterial.SoundGroup::class.java, Deserializers.SoundGroup)
        registerTypeAdapter(CItemStack.Size::class.java, Deserializers.Size)
        registerTypeAdapter(TextComponent::class.java, Deserializers.TextComponents)
        registerTypeAdapter(StatusEffectInstance::class.java, Deserializers.StatusEffect)
        registerTypeAdapter(SoundEvent::class.java, Deserializers.Sound)
        registerTypeAdapter(Identifier::class.java, Deserializers.Id)
    }.create()
    private val directory = File(FabricLoader.INSTANCE.gameDirectory, "./crafty/").toPath()
    private val contentPacks = HashSet<String>()
    private val modules = mutableSetOf(BlockModule, ItemModule)

    override fun onInitialize() {
        CompatLoader.load()
        checkDirs()
        modules.forEach(::loadModule)
    }

    private fun checkDirs() {
        arrayOf(directory).forEach {
            if (Files.notExists(it)) {
                Files.createDirectory(it)
            }
        }
    }

    private fun loadModule(module: Module) {
        val dir = module.name

        Files.newDirectoryStream(directory).forEach {
            val pack = it.fileName.toString()
            contentPacks += pack
            logger.info("[Crafty] Loading content pack $pack: $dir")
            if (Files.isDirectory(it)) Files.newDirectoryStream(it).forEach { l2 ->
                if (Files.isDirectory(l2) && l2.fileName.toString() == dir)
                    Files.newDirectoryStream(l2).forEach { file ->
                        if (file.toFile().extension == "json") {
                            module.loadContent(file)
                        }
                    }
            }
        }
    }

    fun registerModule(module: Module) {
        modules += module
    }
}
