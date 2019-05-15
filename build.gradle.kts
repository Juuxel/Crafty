import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version Versions.KOTLIN
    idea
    id("fabric-loom") version Versions.LOOM
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

configurations {
    create("shadow")
    this["compile"].extendsFrom(this["shadow"])
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

    // Other libraries
    add("shadow", "com.github.anymaker:tnjson:" + Versions.TNJSON)
}

tasks.withType<Jar> {
    from(configurations["shadow"].asFileTree.files.map { zipTree(it) })
}

tasks.getByName<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") {
        expand(
            mutableMapOf(
                "modVersion" to Versions.MOD
            )
        )
    }
}
