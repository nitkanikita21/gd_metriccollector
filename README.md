# Godlike Metric Collector

A Minecraft mod for collecting server metrics (TPS and MSPT) and sending them to a Prometheus Pushgateway. Supports both
Fabric and NeoForge platforms.

## Building

The project uses Gradle as the build system. To build the mod:

1. Clone the repository
2. Build container for build:
   ```bash
   docker build \
    [--build-arg BASE_IMAGE=eclipse-temurin:21-jdk-jammy] \   # <- use specific JDK for minecraft versions 
    -t mc-mod-env .
    ```
3. Start container for build:
   ```bash
   docker run --rm -it \
    -v "$PWD:/workspace" \
    -v "$PWD/.gradle-build:/home/gradle/.gradle" \
    <CONTAINER_NAME>
   ```
4. Run the build command inside the container


Specific commands for building specific modules. Replace `<MC_VERSION>` with the available version of Minecraft.
1. **NeoForged**: `./gradlew :neoforge:neoforge-<MC_VERSION>:shadowJar`
2. **Spigot**: `./gradlew :neoforge:spigot-1.20:shadowJar`
3. **Fabric**: `./gradlew :fabric:fabric-<MC_VERSION>:build`
