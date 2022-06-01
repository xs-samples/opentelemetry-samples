# 目录结构说明

```shell
.
├── README.md ---------------------------------------------- 说明文档
├── docker ------------------------------------------------- docker 容器相关
│   ├── docker-compose.yaml -------------------------- docker compose 编排文件
│   ├── java ----------------------------------------- Java 应用相关
│   │   ├── Dockerfile ------------------------- Java 镜像构建文件
│   │   ├── application.jar -------------------- Java Spring boot 启动 jar，由 maven 打包后生成
│   │   ├── bootstrap.sh ----------------------- Java 启动脚本，包含 OTel javaagent 配置
│   │   └── opentelemetry-javaagent.jar -------- OTel javaagent jar
│   └── python --------------------------------------- Python 应用相关
│       ├── client ----------------------------------- Python 客户端
│       └── server ----------------------------------- Python 服务端
├── docker-compose.sh -------------------------------------- docker-compose 执行脚本，需要定位到该目录时执行，否则会有执行上下文问题
├── pom.xml ------------------------------------------------ pom.xml 文件
└── src ---------------------------------------------------- Java 应用源码目录
```

# 调用链说明
![](http://cdn.0512.host/images/20220601145152.png)

# 使用说明

## 构建镜像并启动容器

```shell
# 进入 opentelemetry-python-java 根目录
./docker-compose.sh
```

## 容器说明

```shell
$ docker-compose ps -a                              
               Name                          Command            State                                                                                  Ports                                                                               
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
docker_jaeger_1                      /go/bin/all-in-one-linux   Up      0.0.0.0:14250->14250/tcp,:::14250->14250/tcp, 0.0.0.0:14268->14268/tcp,:::14268->14268/tcp, 0.0.0.0:14269->14269/tcp,:::14269->14269/tcp,                          
                                                                        0.0.0.0:16686->16686/tcp,:::16686->16686/tcp, 0.0.0.0:5775->5775/udp,:::5775->5775/udp, 0.0.0.0:5778->5778/tcp,:::5778->5778/tcp,                                  
                                                                        0.0.0.0:6831->6831/udp,:::6831->6831/udp, 0.0.0.0:6832->6832/udp,:::6832->6832/udp, 0.0.0.0:9411->9411/tcp,:::9411->9411/tcp                                       
docker_otel-java-sample_1            entrypoint                 Up                                                                                                                                                                         
docker_otel-python-client-sample_1   entrypoint lazy            Up                                                                                                                                                                         
docker_otel-python-server-sample_1   entrypoint                 Up
```
### docker_jaeger_1

Jaeger 容器

### docker_otel-java-sample_1

Java 应用容器

### docker_otel-python-client-sample_1

Python 客户端容器

### docker_otel-python-server-sample_1

Python 服务端容器

### 进入容器

```shell
# 进入客户端容器
docker exec -it docker_otel-python-client-sample_1 zsh
# 手动触发客户端请求
./client.sh
```

# 运行日志说明

```shell
otel-python-client-sample_1  | -------------- Python client request java server got：Python served:otel-python-test
otel-python-client-sample_1  | {
otel-python-client-sample_1  |     "name": "HTTP GET",
otel-python-client-sample_1  |     "context": {
otel-python-client-sample_1  |         "trace_id": "0xb3f234f45b9dd860becba545713cb6c4",
otel-python-client-sample_1  |         "span_id": "0x5a947dbe84dd0175",
otel-python-client-sample_1  |         "trace_state": "[]"
otel-python-client-sample_1  |     },
otel-python-client-sample_1  |     "kind": "SpanKind.CLIENT",
otel-python-client-sample_1  |     "parent_id": "0x2ffb6f8e2f7b2890",
otel-python-client-sample_1  |     "start_time": "2022-06-01T07:09:45.918981Z",
otel-python-client-sample_1  |     "end_time": "2022-06-01T07:09:47.465122Z",
otel-python-client-sample_1  |     "status": {
otel-python-client-sample_1  |         "status_code": "UNSET"
otel-python-client-sample_1  |     },
otel-python-client-sample_1  |     "attributes": {
otel-python-client-sample_1  |         "http.method": "GET",
otel-python-client-sample_1  |         "http.url": "http://java-end:8081/greeting?param=otel-python-test",
otel-python-client-sample_1  |         "http.status_code": 200
otel-python-client-sample_1  |     },
otel-python-client-sample_1  |     "events": [],
otel-python-client-sample_1  |     "links": [],
otel-python-client-sample_1  |     "resource": {
otel-python-client-sample_1  |         "telemetry.sdk.language": "python",
otel-python-client-sample_1  |         "telemetry.sdk.name": "opentelemetry",
otel-python-client-sample_1  |         "telemetry.sdk.version": "1.11.1",
otel-python-client-sample_1  |         "service.name": "python-client",
otel-python-client-sample_1  |         "telemetry.auto.version": "0.30b1"
otel-python-client-sample_1  |     }
otel-python-client-sample_1  | }
otel-python-client-sample_1  | {
otel-python-client-sample_1  |     "name": "HTTP GET",
otel-python-client-sample_1  |     "context": {
otel-python-client-sample_1  |         "trace_id": "0xb3f234f45b9dd860becba545713cb6c4",
otel-python-client-sample_1  |         "span_id": "0x2ffb6f8e2f7b2890",
otel-python-client-sample_1  |         "trace_state": "[]"
otel-python-client-sample_1  |     },
otel-python-client-sample_1  |     "kind": "SpanKind.CLIENT",
otel-python-client-sample_1  |     "parent_id": null,
otel-python-client-sample_1  |     "start_time": "2022-06-01T07:09:45.898186Z",
otel-python-client-sample_1  |     "end_time": "2022-06-01T07:09:47.465804Z",
otel-python-client-sample_1  |     "status": {
otel-python-client-sample_1  |         "status_code": "UNSET"
otel-python-client-sample_1  |     },
otel-python-client-sample_1  |     "attributes": {
otel-python-client-sample_1  |         "http.method": "GET",
otel-python-client-sample_1  |         "http.url": "http://java-end:8081/greeting",
otel-python-client-sample_1  |         "http.status_code": 200
otel-python-client-sample_1  |     },
otel-python-client-sample_1  |     "events": [],
otel-python-client-sample_1  |     "links": [],
otel-python-client-sample_1  |     "resource": {
otel-python-client-sample_1  |         "telemetry.sdk.language": "python",
otel-python-client-sample_1  |         "telemetry.sdk.name": "opentelemetry",
otel-python-client-sample_1  |         "telemetry.sdk.version": "1.11.1",
otel-python-client-sample_1  |         "service.name": "python-client",
otel-python-client-sample_1  |         "telemetry.auto.version": "0.30b1"
otel-python-client-sample_1  |     }
otel-python-client-sample_1  | }
```

> `context.trace_id` 为链路 ID：0xb3f234f45b9dd860becba545713cb6c4

# 查询链路说明

```shell
# 去掉 traceId 前缀的 `0x` 字符后查询
http://localhost:16686/trace/b3f234f45b9dd860becba545713cb6c4
```

## 链路效果图

![](http://cdn.0512.host/images/20220601151500.png)