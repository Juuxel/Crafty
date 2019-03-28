import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.20"
    idea
    id("fabric-loom") version "0.2.0-SNAPSHOT"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

base {
    archivesBaseName = "Crafty"
}

version = "0.3.0"

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
	compile(kotlin("stdlib-jdk8"))
	minecraft("com.mojang:minecraft:19w13a")
	mappings("net.fabricmc:yarn:19w13a.3")
	modCompile("net.fabricmc:fabric-loader:0.3.7.109")
    implementation("net.fabricmc:fabric-language-kotlin:1.3.21-SNAPSHOT")
	modCompile("net.fabricmc:fabric:0.2.6.117")

    // Other mods
    modCompile("towelette:Towelette:1.4.0")

    // Other libraries
    add("shadow", "com.github.anymaker:tnjson:1.2")
}

tasks.withType<Jar> {
    from(configurations["shadow"].asFileTree.files.map { zipTree(it) })
}
