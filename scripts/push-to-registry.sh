#!/usr/bin/env bash

service_version=v1.2
postgres_version=15
grafana_version=9.4.7
prometheus_version=v2.41.0
node_exporter_version=v2.41.0
postgres_exporter_version=0.13.2

usage() {
  cat <<EOF
Usage: push-to-registry <[options]>
Options:
  -s --service        Camunda Service version ($service_version)
  -p --postgres       Postgres version ($postgres_version)
  -h                  Print this help
EOF
  exit 1
}

if ! options=$(getopt -o s:p: -l service:,postgres: -- "$@"); then
  exit 1
fi

set -- "$options"

while [ $# -gt 0 ]; do
  case $1 in
  -s | --service) service_version=$2 ;;
  -p | --postgres) postgres_version=$2 ;;
  -h) usage ;;
  esac
  shift 1
done

images=(
  "romanowalex/camunda-base:$service_version"
  "postgres:$postgres_version"
  "grafana/grafana:$grafana_version"
  "prom/prometheus:$prometheus_version"
  "prom/node-exporter:$node_exporter_version"
  "bitnami/postgres-exporter:$postgres_exporter_version"
)

for image in "${images[@]}"; do
  echo "Pushing $image to local registry"
  docker tag "$image" 127.0.0.1:5000/"$image"
  docker push 127.0.0.1:5000/"$image"
done
