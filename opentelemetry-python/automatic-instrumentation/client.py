from sys import argv

from requests import get

from opentelemetry import trace
from opentelemetry.propagate import inject
from opentelemetry.sdk.trace import TracerProvider
from opentelemetry.sdk.trace.export import (
    BatchSpanProcessor,
    ConsoleSpanExporter,
)

trace.set_tracer_provider(TracerProvider())
tracer = trace.get_tracer_provider().get_tracer(__name__)

trace.get_tracer_provider().add_span_processor(
    BatchSpanProcessor(ConsoleSpanExporter())
)


assert len(argv) == 2

with tracer.start_as_current_span("client"):

    with tracer.start_as_current_span("client-server"):
        headers = {}
        inject(headers)
        requested = get(
            "http://localhost:4082/server_request",
            params={"param": argv[1]},
            headers=headers,
        )

        assert requested.status_code == 200