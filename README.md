# Camunda base

[![Build project](https://github.com/Romanow/camunda-base/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/camunda-base/actions/workflows/build.yml)

## Run Camunda locally

```shell
$ ./gradlew clean build
$ docker compose up -d
```

## Run Camunda in k8s

```shell
$ minikube start --cpus=4 --memory=6g --driver=qemu2
$ helm repo add romanow https://romanow.github.io/helm-charts/
$ helm search repo romanow
$ minukube image load postgres:15
$ helm install postgres --values k8s/postgres/values.yaml romanow/postgres
```
