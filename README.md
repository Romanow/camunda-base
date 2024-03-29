# Camunda base

[![Build project](https://github.com/Romanow/camunda-base/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/camunda-base/actions/workflows/build.yml)
[![pre-commit](https://img.shields.io/badge/pre--commit-enabled-brightgreen?logo=pre-commit)](https://github.com/pre-commit/pre-commit)

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

```shell
# build  project
$ docker compose build

# run
$ docker compose up -d

# cleanup resources
$ docker compose down -v

```

После этого открываем в браузере [http://localhost:8080](http://localhost:8080/) и вводим `admin` / `admin`.
