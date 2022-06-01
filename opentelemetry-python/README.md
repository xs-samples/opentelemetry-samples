# 目录结构说明
```shell
.
├── automatic-instrumentation ------------------- 自动检测(https://opentelemetry.io/docs/instrumentation/python/automatic/)，使用:jaeger thrift http
│  ├── client.py -------------------------------- 客户端，执行后会调用服务端，端口：8082
│  ├── client.sh -------------------------------- 客户端，执行脚本
│  ├── server.py -------------------------------- 服务端，为客户端提供服务，模拟常规接口调用
│  └── server.sh -------------------------------- 服务端，执行脚本
├── manual-instrumentation ---------------------- 手动检测(https://opentelemetry.io/docs/instrumentation/python/manual/)，使用:jaeger gRPC/thrift http
│  ├── jaeger_exporter_grpc.py ------------------ Jaeger gRPC 导出器
│  ├── jaeger_exporter_grpc.sh ------------------ Jaeger gRPC 导出器，执行脚本
│  ├── jaeger_exporter_thrift.py ---------------- Jaeger Thrift 导出器
│  └── jaeger_exporter_thrift.sh ---------------- Jaeger Thrift 导出器，执行脚本
├── Dockerfile ----------------------------------- Python 运行环境容器，已包含相关 pip 包的定制容器
├── bootstrap.sh -------------------------------- 启动脚本，分别调用自动仪表、手动仪表相关的执行 Shell 脚本
├── datasource.yaml ----------------------------- Grafana 源数据源配置文件
├── docker-compose-jaeger.yaml ------------------ Jaeger docker 编排文件
├── docker-compose-tempo.yaml ------------------- Tempo docker 编排文件
├── docker-compose.sh --------------------------- Docker Compose 执行脚本
├── README.md ----------------------------------- 说明文档
└── tempo.yaml ---------------------------------- Tempo 容器配置文件
```

# 使用说明

## 构建镜像并启动容器

```shell
# 后端使用 Jaeger
./docker-compose.sh jaeger
# Or，后端使用 Tempo
./docker-compose.sh tempo
```

## 容器说明

```shell
# Jaeger 后端
$ docker-compose -f docker-compose-jaeger.yaml ps -a       
                  Name                              Command            State                                                                              Ports                                                                            
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
opentelemetry-python_jaeger_1               /go/bin/all-in-one-linux   Up      0.0.0.0:14250->14250/tcp,:::14250->14250/tcp, 0.0.0.0:14268->14268/tcp,:::14268->14268/tcp, 0.0.0.0:14269->14269/tcp,:::14269->14269/tcp,                   
                                                                               0.0.0.0:16686->16686/tcp,:::16686->16686/tcp, 0.0.0.0:5775->5775/udp,:::5775->5775/udp, 0.0.0.0:5778->5778/tcp,:::5778->5778/tcp,                           
                                                                               0.0.0.0:6831->6831/udp,:::6831->6831/udp, 0.0.0.0:6832->6832/udp,:::6832->6832/udp, 0.0.0.0:9411->9411/tcp,:::9411->9411/tcp                                
opentelemetry-python_otel-python-sample_1   entrypoint zsh             Up

# Tempo 后端
$ docker-compose -f docker-compose-tempo.yaml ps -a
                  Name                                 Command               State                                                                  Ports                                                                
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
opentelemetry-python_grafana_1              /run.sh                          Up      0.0.0.0:3000->3000/tcp,:::3000->3000/tcp                                                                                            
opentelemetry-python_otel-python-sample_1   entrypoint zsh                   Up                                                                                                                                          
opentelemetry-python_tempo_1                /tempo -config.file=/etc/t ...   Up      0.0.0.0:14250->14250/tcp,:::14250->14250/tcp, 0.0.0.0:14268->14268/tcp,:::14268->14268/tcp, 0.0.0.0:3200->3200/tcp,:::3200->3200/tcp
```

### opentelemetry-python_jaeger_1

jaeger 容器

### opentelemetry-python_otel-python-sample_1

Python 示例容器

### opentelemetry-python_grafana_1

当使用 `Tempo` 时，Tempo Grafana 查询面板

## 运行日志说明

```shell
python3-otel-example_1  |  Starting Client... 
python3-otel-example_1  | {
python3-otel-example_1  |     "name": "client-server",
python3-otel-example_1  |     "context": {
python3-otel-example_1  |         "trace_id": "0xe8d7f6bc7f85d5068c9d60e5c3f5a07a",
python3-otel-example_1  |         "span_id": "0x70da62b26ebdfa3c",
python3-otel-example_1  |         "trace_state": "[]"
python3-otel-example_1  |     },
python3-otel-example_1  |     "kind": "SpanKind.INTERNAL",
python3-otel-example_1  |     "parent_id": "0x5ca981a4d24c4d18",
python3-otel-example_1  |     "start_time": "2022-05-18T10:08:57.349127Z",
python3-otel-example_1  |     "end_time": "2022-05-18T10:08:57.396925Z",
python3-otel-example_1  |     "status": {
python3-otel-example_1  |         "status_code": "UNSET"
python3-otel-example_1  |     },
python3-otel-example_1  |     "attributes": {},
python3-otel-example_1  |     "events": [],
python3-otel-example_1  |     "links": [],
python3-otel-example_1  |     "resource": {
python3-otel-example_1  |         "telemetry.sdk.language": "python",
python3-otel-example_1  |         "telemetry.sdk.name": "opentelemetry",
python3-otel-example_1  |         "telemetry.sdk.version": "1.12.0rc1",
python3-otel-example_1  |         "service.name": "python-thrift-client-automatic",
python3-otel-example_1  |         "telemetry.auto.version": "0.31b0"
python3-otel-example_1  |     }
python3-otel-example_1  | }
```

其中的`context.trace_id`为链路 id，如上：`0xe8d7f6bc7f85d5068c9d60e5c3f5a07a`

## 查询链路

```shell
# 后端使用 Jaeger 时
http://localhost:16686/trace/e8d7f6bc7f85d5068c9d60e5c3f5a07a
# 后端使用 Tempo 时
http://localhost:3000/explore
```

> 查询时需要去掉`trace_id`前面的`0x`

### Jaeger 链路查询效果
![](http://cdn.0512.host/images/20220520133924.png)

### Tempo 链路查询效果
![](http://cdn.0512.host/images/20220518181013.png)

## 进入示例容器

```shell
# opentelemetry-python_python3-otel-example_1：容器名称
docker exec -it opentelemetry-python_otel-python-sample_1 zsh

# 启动，automatic/manual 脚本
./bootstrap.sh

# 启动服务端（automatic）
./automatic-instrumentation/server.sh
# 启动客户端（automatic）
./automatic-instrumentation/client.sh

# 导出数据使用 Thrift 协议（manual）
./manual-instrumentation/jaeger_exporter_thrift.sh
# 导出数据使用 gRPC 协议（manual）
./manual-instrumentation/jaeger_exporter_grpc.sh
```

> 遗留问题说明：
> 
> 1.opentelemetry-instrument 不支持 insecure 参数已提 [PR](https://github.com/open-telemetry/opentelemetry-python/pull/2696)
> 
> 2.Exporter 使用 gRPC 协议，导出服务端为 Tempo（1.4.1）版本时，`trace_id`能查询，但无数据。已提 [Issue](https://github.com/grafana/tempo/issues/1432)