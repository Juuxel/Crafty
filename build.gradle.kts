import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.11"
    idea
    id("fabric-loom") version "0.1.0-SNAPSHOT"
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
}

dependencies {
	compile(kotlin("stdlib-jdk8"))
	minecraft("com.mojang:minecraft:18w50a")
	mappings("net.fabricmc:yarn:18w50a.92")
	modCompile("net.fabricmc:fabric-loader:0.3.0.75")
    modCompile("net.fabricmc:fabric-language-kotlin:1.3.10-26")
    compileOnly("net.fabricmc:fabric-language-kotlin:1.3.10-26")
	modCompile("net.fabricmc:fabric:0.1.2.64")

    // Other mods
    modCompile(files("libs/Towelette-1.1.3.0-dev.jar"))
    modCompile(files("libs/WateredDown-0.3.0-dev.jar"))
    modCompile(files("../Palette/build/libs/Palette-0.1.0-dev.jar"))

    // Other libraries
    add("shadow", "com.github.anymaker:tnjson:1.2")
}

tasks.withType<Jar> {
    from(configurations["shadow"].asFileTree.files.map { zipTree(it) })
}
