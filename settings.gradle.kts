pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.minecraftforge.net")
    }
}

rootProject.name = "minecraft-multiplatform-mod"

include("common")

//include("forge:forge-1.20.1")

include("neoforge:neoforge-1.21.1")
include("neoforge:neoforge-1.21.4")
include("neoforge:neoforge-1.21.5")

include("fabric:fabric-1.20.1")
include("fabric:fabric-1.20.4")
include("fabric:fabric-1.21.1")
include("fabric:fabric-1.21.4")
include("fabric:fabric-1.21.5")

include("spigot:spigot-1.20")