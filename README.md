[![CI](https://github.com/Romanow/camunda-base/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/camunda-base/actions/workflows/build.yml)
[![pre-commit](https://img.shields.io/badge/pre--commit-enabled-brightgreen?logo=pre-commit)](https://github.com/pre-commit/pre-commit)
[![Release](https://img.shields.io/github/v/release/Romanow/camunda-base?logo=github&sort=semver)](https://github.com/Romanow/camunda-base/releases/latest)
[![Camunda](https://img.shields.io/docker/pulls/romanowalex/camunda-base?logo=docker)](https://hub.docker.com/r/romanowalex/camunda-base)

# Camunda base

## Необходимые зависимости

* Docker – [Install Docker Engine](https://docs.docker.com/engine/install/)
* Camunda Modeller – [Download Desktop Modeler](https://camunda.com/download/modeler/)

### Установка Token Simulator

```shell
$ cd ~/Library/Application\ Support/camunda-modeler
$ mkdir -p resources/plugins && cd resources/plugins
$ git clone https://github.com/camunda/camunda-modeler-token-simulation-plugin
```

## Локальный запуск

Использовать [Docker Compose](docker-compose.yml):

```shell
$ docker compose up -d --wait

```

После этого открываем в браузере [http://localhost:8080](http://localhost:8080/) и вводим `admin` / `admin`.
