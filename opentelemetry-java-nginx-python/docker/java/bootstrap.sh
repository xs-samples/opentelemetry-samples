#!/usr/bin/env bash

echo -e "\033[41;33m Starting Server... \033[0m"

java -javaagent:$OTEL_WORKDIR/opentelemetry-javaagent.jar \
     -Dotel.traces.exporter=jaeger \
     -Dotel.exporter.jaeger.endpoint=http://backend:14250 \
     -Dotel.metrics.exporter=none \
     -Dotel.resource.attributes=service.name=java-server \
     -Dotel.javaagent.debug=true \
     -jar $OTEL_WORKDIR/application.jar