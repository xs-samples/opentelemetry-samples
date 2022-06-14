from flask import Flask, request

app = Flask(__name__)


@app.route("/server_request")
def server_request():
    print(request.args.get("param"))
    return "Python served:" + request.args.get("param")


if __name__ == "__main__":
    # 当 Flask 开启调试模式时，调试模式可以阻止检测的发生，因为它启用了重新加载器。要在启用调试模式时运行检测，请将 use_reloader 选项设置为False
    app.run(host='0.0.0.0', port=4082, debug=True, use_reloader=False)
