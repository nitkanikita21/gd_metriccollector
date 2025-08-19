plugins {
    `java-library`
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.slf4j:slf4j-api:2.0.1")
}
