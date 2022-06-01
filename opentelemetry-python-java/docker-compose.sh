#!/usr/bin/env bash

__main() {

  mvn clean package

  __batch_exec_docker_compose docker/docker-compose.yaml
}

__batch_exec_docker_compose() {
  local docker_compose_filename=$1
    __exec_docker_compose $docker_compose_filename down && \
    __exec_docker_compose $docker_compose_filename build && \
    __exec_docker_compose $docker_compose_filename up
}

__exec_docker_compose() {
  docker-compose -f $1 $2
}

__main
