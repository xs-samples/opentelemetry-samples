# 目录结构说明

```shell
.
├── README.md ---------------------------------------------- 说明文档
├── docker ------------------------------------------------- docker 容器相关
│   ├── docker-compose.yaml -------------------------- docker compose 编排文件
│   ├── nginx ---------------------------------------- Nginx 相关
│   ├── java ----------------------------------------- Java 应用相关
│   └── python --------------------------------------- Python 应用相关
├── docker-compose.sh -------------------------------------- docker-compose 执行脚本，需要定位到该目录时执行，否则会有执行上下文问题
├── pom.xml ------------------------------------------------ pom.xml 文件
└── src ---------------------------------------------------- Java 应用源码目录
```

# 调用链说明
![](http://cdn.0512.host/images/20220602074455.png)

# 使用说明

## 构建镜像并启动容器

```shell
# 进入 opentelemetry-java-nginx-python 根目录
./docker-compose.sh
```

## 容器说明

```shell
$ docker-compose ps -a
               Name                             Command               State                                                                               Ports                                                                            
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
docker_jaeger_1                      /go/bin/all-in-one-linux         Up      0.0.0.0:14250->14250/tcp,:::14250->14250/tcp, 0.0.0.0:14268->14268/tcp,:::14268->14268/tcp, 0.0.0.0:14269->14269/tcp,:::14269->14269/tcp,                    
                                                                              0.0.0.0:16686->16686/tcp,:::16686->16686/tcp, 0.0.0.0:5775->5775/udp,:::5775->5775/udp, 0.0.0.0:5778->5778/tcp,:::5778->5778/tcp,                            
                                                                              0.0.0.0:6831->6831/udp,:::6831->6831/udp, 0.0.0.0:6832->6832/udp,:::6832->6832/udp, 0.0.0.0:9411->9411/tcp,:::9411->9411/tcp                                 
docker_otel-java-sample_1            entrypoint                       Up      0.0.0.0:4081->4081/tcp,:::4081->4081/tcp                                                                                                                     
docker_otel-nginx-sample_1           /docker-entrypoint.sh ngin ...   Up      80/tcp                                                                                                                                                       
docker_otel-python-server-sample_1   entrypoint                       Up
```
### docker_jaeger_1

Jaeger 容器

### docker_otel-java-sample_1

Java 应用容器

### docker_otel-python-server-sample_1

Python 服务端容器

### docker_otel-nginx-sample_1

Nginx 容器

### 请求调用

```shell
curl --location --request GET 'http://localhost:4081/greeting?param=otel-test' \
--header 'User-Agent: apifox/1.0.0 (https://www.apifox.cn)'
```

# 运行日志说明

```shell
otel-python-server-sample_1  | {
otel-python-server-sample_1  |     "name": "/server_request",
otel-python-server-sample_1  |     "context": {
otel-python-server-sample_1  |         "trace_id": "0x5de4e965b8d97a289043e7583c92fcd9",
otel-python-server-sample_1  |         "span_id": "0x63f1f6981239d423",
otel-python-server-sample_1  |         "trace_state": "[]"
otel-python-server-sample_1  |     },
otel-python-server-sample_1  |     "kind": "SpanKind.SERVER",
otel-python-server-sample_1  |     "parent_id": "0xe72e61375749a7a0",
otel-python-server-sample_1  |     "start_time": "2022-06-01T11:12:21.490591Z",
otel-python-server-sample_1  |     "end_time": "2022-06-01T11:12:21.493429Z",
otel-python-server-sample_1  |     "status": {
otel-python-server-sample_1  |         "status_code": "UNSET"
otel-python-server-sample_1  |     },
otel-python-server-sample_1  |     "attributes": {
otel-python-server-sample_1  |         "http.method": "GET",
otel-python-server-sample_1  |         "http.server_name": "0.0.0.0",
otel-python-server-sample_1  |         "http.scheme": "http",
otel-python-server-sample_1  |         "net.host.port": 4082,
otel-python-server-sample_1  |         "http.host": "python-end:4082",
otel-python-server-sample_1  |         "http.target": "/server_request?param=otel-test",
otel-python-server-sample_1  |         "net.peer.ip": "172.22.0.4",
otel-python-server-sample_1  |         "http.user_agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36 Hutool",
otel-python-server-sample_1  |         "net.peer.port": 40002,
otel-python-server-sample_1  |         "http.flavor": "1.0",
otel-python-server-sample_1  |         "http.route": "/server_request",
otel-python-server-sample_1  |         "http.status_code": 200
otel-python-server-sample_1  |     },
otel-python-server-sample_1  |     "events": [],
otel-python-server-sample_1  |     "links": [],
otel-python-server-sample_1  |     "resource": {
otel-python-server-sample_1  |         "telemetry.sdk.language": "python",
otel-python-server-sample_1  |         "telemetry.sdk.name": "opentelemetry",
otel-python-server-sample_1  |         "telemetry.sdk.version": "1.11.1",
otel-python-server-sample_1  |         "service.name": "python-server",
otel-python-server-sample_1  |         "telemetry.auto.version": "0.30b1"
otel-python-server-sample_1  |     }
otel-python-server-sample_1  | }
```

> `context.trace_id` 为链路 ID：0x5de4e965b8d97a289043e7583c92fcd9

# 查询链路说明

```shell
# 去掉 traceId 前缀的 `0x` 字符后查询
http://localhost:16686/trace/5de4e965b8d97a289043e7583c92fcd9
```

## 链路效果图

![](http://cdn.0512.host/images/20220602074850.png)