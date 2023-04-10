# Camunda base

[![Build project](https://github.com/Romanow/camunda-base/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/camunda-base/actions/workflows/build.yml)

## Build and run

```shell
# run postgres 15 in docker
$ docker compose up -d

# build project
$ ./gradlew clean build

# run Camunda
$ ./gradlew bootRun

# cleanup resources
$ docker compose down -v
```

## Requirements

1. [Java 11](https://formulae.brew.sh/formula/openjdk@11).
2. [Docker](https://docs.docker.com/desktop/install/mac-install/).