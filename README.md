# Camunda base

[![Build project](https://github.com/Romanow/camunda-base/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/camunda-base/actions/workflows/build.yml)

## Run Camunda locally

```shell
# build service
$ ./gradlew clean build

# build docker image
$ docker compose build

# create docker swarm cluster
$ docker swarm init

# create internal docker registry to work with local images
$ docker service create --name registry --publish 5000:5000 registry:2.8

# push local images to registry
$ ./scripts/push-to-registry.sh

# run camunda
$ docker stack deploy -c docker-compose.yml camunda

# list running services
$ docker stack services camunda
```

## Run Camunda in k8s

```shell
# start minikube
$ minikube start --cpus=4 --memory=6g --driver=qemu2

# add repo with charts
$ helm repo add romanow https://romanow.github.io/helm-charts/

$ helm search repo romanow
# upload postgres and camunda service
$ minukube image load postgres:15
$ minukube image load romanowalex/camunda-base:v1.1
#
$ helm install postgres --values k8s/postgres/values.yaml romanow/postgres
```
