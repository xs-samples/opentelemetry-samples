#!/usr/bin/env bash

echo -e "\033[41;33m Starting Client... \033[0m"

if [ "$1" == 'lazy' ]; then
  echo -e "\033[41;33m Starting Client...waiting for 30s \033[0m"
  sleep 30s
fi

opentelemetry-instrument \
--exporter_jaeger_endpoint http://backend:14268/api/traces?format=jaeger.thrift \
--service_name python-client \
--traces_exporter jaeger_thrift,console \
--logs_exporter console \
python $OTEL_WORKDIR/client.py otel-python-test

exec "zsh"
