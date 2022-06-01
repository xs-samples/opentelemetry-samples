from flask import Flask, request
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

app = Flask(__name__)


@app.route("/server_request")
def server_request():
    with tracer.start_as_current_span("server"):
        print(request.args.get("param"))
        return "served"


if __name__ == "__main__":
    # 当 Flask 开启调试模式时，调试模式可以阻止检测的发生，因为它启用了重新加载器。要在启用调试模式时运行检测，请将 use_reloader 选项设置为False
    app.run(host='0.0.0.0', port=8082, debug=True, use_reloader=False)
