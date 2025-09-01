plugins {
    `java-library`
    `maven-publish`
    id("net.neoforged.gradle.userdev") version "7.0.190"
    id("com.gradleup.shadow") version "9.0.2"
}

tasks.register<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.BIN
}


// Інлайн значення пропертів тут:
val mod_id = "metric_collector"
val mod_version = "1.0.0"
val mod_name = "metric_collector"
val mod_license = "MIT"
val mod_authors = "nitkanikita21"
val mod_description = "metric_collector"
val mod_group_id = "net.nitkanikita21.metric_collector"

val minecraft_version = "1.21.4"
val minecraft_version_range = "[1.21.4]"
val neo_version = "21.4.147"
val loader_version_range = "[1,)"

version = mod_version
group = mod_group_id

repositories {
    // Додай, якщо потрібно, репозиторії, наприклад:
    mavenCentral()
}

base {
    archivesName.set("${mod_id}_neoforge_${minecraft_version}+${neo_version}")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.BIN
}

runs {
    // Цей блок налаштовує всі run-конфіги
    configureEach {
        systemProperty("forge.logging.markers", "REGISTRIES")
        systemProperty("forge.logging.console.level", "debug")
        modSource(sourceSets["main"])
    }

    // Конфігурація client
    create("client") {
        systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
    }

    // Конфігурація server
    create("server") {
        systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        argument("--nogui")
    }

}

/*configurations {
    create("localRuntime") {
        extendsFrom(configurations.runtimeClasspath.get())
    }
}*/

dependencies {
    compileOnly("net.neoforged:neoforge:$neo_version")
    implementation(project(":common"))


    // Додай сюди свої залежності
}

tasks.withType<ProcessResources>().configureEach {
    val replaceProperties = mapOf(
        "minecraft_version" to minecraft_version,
        "minecraft_version_range" to minecraft_version_range,
        "neo_version" to neo_version,
        "loader_version_range" to loader_version_range,
        "mod_id" to mod_id,
        "mod_name" to mod_name,
        "mod_license" to mod_license,
        "mod_version" to mod_version,
        "mod_authors" to mod_authors,
        "mod_description" to mod_description
    )
    inputs.properties(replaceProperties)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(replaceProperties)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("${project.projectDir}/repo")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.shadowJar {
    isZip64 = true
}
