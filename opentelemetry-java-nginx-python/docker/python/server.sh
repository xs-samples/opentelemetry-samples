#!/usr/bin/env bash

opentelemetry-instrument \
--exporter_jaeger_endpoint http://backend:14268/api/traces?format=jaeger.thrift \
--service_name python-server \
--traces_exporter jaeger_thrift,console \
python $OTEL_WORKDIR/server.py