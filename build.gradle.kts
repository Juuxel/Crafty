import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version Versions.KOTLIN
    idea
    id("fabric-loom") version Versions.LOOM
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

base {
    archivesBaseName = "Crafty"
}

version = Versions.MOD

minecraft {
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    mavenCentral()
    maven(url = "https://minecraft.curseforge.com/api/maven") {
        name = "CurseForge"
    }
}

dependencies {
    minecraft("com.mojang:minecraft:" + Versions.MINECRAFT)
    mappings("net.fabricmc:yarn:" + Versions.MAPPINGS)
    modCompile("net.fabricmc:fabric-loader:" + Versions.FABRIC_LOADER)
    modCompile("net.fabricmc:fabric-language-kotlin:" + Versions.FABRIC_KOTLIN)
    compileOnly("net.fabricmc:fabric-language-kotlin:" + Versions.FABRIC_KOTLIN)
    modCompile("net.fabricmc.fabric-api:fabric-api:" + Versions.FABRIC)

    // Other mods
    modCompile("towelette:Towelette:" + Versions.TOWELETTE)
    shadow("blue.endless:jankson:" + Versions.JANKSON)
}

tasks.getByName<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") {
        expand("modVersion" to Versions.MOD)
    }
}

tasks.getByName<ShadowJar>("shadowJar") {
    relocate("blue.endless.jankson", "juuxel.crafty.repackage.blue.endless.jankson")
    classifier = "shadow"
    configurations = listOf(project.configurations.getByName("shadow"))
}
