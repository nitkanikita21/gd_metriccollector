# Godlike Metric Collector

A Minecraft mod for collecting server metrics (TPS and MSPT) and sending them to a Prometheus Pushgateway. Supports both
Fabric and NeoForge platforms.

## Building

The project uses Gradle as the build system. To build the mod:

1. Clone the repository
2. Run the following command:
   ```bash
   ./gradlew shadowJar
   ```

## Features

- Collects server TPS (Ticks Per Second)
- Monitors MSPT (Milliseconds Per Tick)
- Automatically sends metrics to Prometheus Pushgateway
- Multi-platform support (Fabric & NeoForge)

## Requirements

- Java 17 or higher
