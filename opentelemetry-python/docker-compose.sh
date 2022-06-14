#!/usr/bin/env bash

__main() {
  local command docker_compose_filename
  command=$1

  if [ -z "$command" ]; then
    echo 'The command line argument is empty.usage:`docker-compose jaeger` `docker-compose tempo`'
    exit 1
  fi

  case "$command" in
  jaeger)
    docker_compose_filename=docker-compose-jaeger.yaml
    ;;
  tempo)
    docker_compose_filename=docker-compose-tempo.yaml
    ;;
  *)
    echo "\`${command}\` Command not found."
    exit 1
    ;;
  esac

  __batch_exec_docker_compose $docker_compose_filename
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

__main $1
