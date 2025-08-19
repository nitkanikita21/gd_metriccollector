plugins {
    id("fabric-loom") version "1.11-SNAPSHOT"
    java
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

val yarnVersion = "1.20.4+build.3"
val loaderVersion = "0.17.2"
val minecraftVersion = "1.20.4"
val fabricVersion = "0.97.3+1.20.4"

val modVersion="1.0.0"
val mavenGroup="com.example"
val archivesBaseName="template-mod"

version = modVersion
group = mavenGroup

dependencies {
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${yarnVersion}:v2")
    modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabricVersion}")
    implementation (project(":common"))
    include  (project(":common"))

}

tasks.withType<JavaCompile>().configureEach {
    options.release = 17
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_20
    targetCompatibility = JavaVersion.VERSION_20
}

base {
    archivesName.set("metriccollector_fabric_${minecraftVersion}+${fabricVersion}")
}

tasks.jar {
    inputs.property("archivesName", project.base.archivesName.get())

    from("LICENSE") {
        rename { fileName ->
            "${fileName}_${inputs.properties["archivesName"]}"
        }
    }
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("loaderVersion", loaderVersion)
    inputs.property("minecraftVersion", minecraftVersion)

    filesMatching("fabric.mod.json") {
        expand(
            mapOf(
                "version" to project.version,
                "loaderVersion" to loaderVersion,
                "minecraftVersion" to minecraftVersion
            )
        )
    }
}

