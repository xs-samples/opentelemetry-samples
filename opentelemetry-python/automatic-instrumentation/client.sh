#!/usr/bin/env bash

opentelemetry-instrument \
--exporter_jaeger_endpoint http://backend:14268/api/traces?format=jaeger.thrift \
--service_name python-thrift-client-automatic \
--traces_exporter jaeger_thrift,console \
--logs_exporter console \
python $OTEL_WORKDIR/automatic-instrumentation/client.py testing