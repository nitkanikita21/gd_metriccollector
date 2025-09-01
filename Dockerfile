# syntax=docker/dockerfile:1.7-labs
ARG BASE_IMAGE=eclipse-temurin:17-jdk-jammy
FROM ${BASE_IMAGE}

# ---- Системні пакети ----
RUN apt-get update && apt-get install -y --no-install-recommends \
    git curl unzip zip ca-certificates bash coreutils \
    && rm -rf /var/lib/apt/lists/*

# ---- Створюємо користувача gradle ----
ARG GRADLE_UID=1000
ARG GRADLE_GID=1000
RUN groupadd -g ${GRADLE_GID} gradle && \
    useradd -m -u ${GRADLE_UID} -g ${GRADLE_GID} -s /bin/bash gradle

USER gradle
WORKDIR /workspace

# ---- Gradle кеш ----
ENV GRADLE_USER_HOME=/home/gradle/.gradle \
    GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.jvmargs='-Xms512m -Xmx3g -XX:+UseG1GC' \
                 -Dorg.gradle.parallel=true -Dorg.gradle.configureondemand=true \
                 -Dorg.gradle.caching=true"

# ---- Опціонально прогріваємо gradle-wrapper ----
COPY --chown=gradle:gradle gradlew ./gradlew
COPY --chown=gradle:gradle gradle/wrapper ./gradle/wrapper
RUN chmod +x ./gradlew || true

# ---- Зручний alias ----
USER root
RUN printf '%s\n' \
'#!/usr/bin/env bash' \
'set -euo pipefail' \
'cd /workspace' \
'./gradlew "$@"' \
> /usr/local/bin/gradle-run && chmod +x /usr/local/bin/gradle-run
USER gradle

CMD [ "bash" ]