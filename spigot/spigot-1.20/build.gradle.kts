plugins {
    java
    id("com.gradleup.shadow") version "9.0.1"
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation(project(":common"))
    compileOnly("org.spigotmc:spigot-api:1.20-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

base {
    archivesName.set("metric_collector_spigot_1.20.1")
}

tasks.shadowJar {
    isZip64 = true
}