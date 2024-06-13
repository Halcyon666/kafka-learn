## Kafka使用问题

### 如何使用Docker Compose部署Kafka

> 借鉴github[仓库](https://github.com/conduktor/kafka-stack-docker-compose/tree/master)
>
> 下面示例使用最简单的 `zk-single-kafka-single.yml`

以下为正确示例：

对仓库中的文件作了两处调整

- 调整了对钱的连接地址

```diff
-       KAFKA_ADVERTISED_LISTENERS: INTERNAL://kafka1:19092,EXTERNAL://${DOCKER_HOST_IP:-127.0.0.1}:9092,DOCKER://host.docker.internal:29092
+      KAFKA_ADVERTISED_LISTENERS: INTERNAL://kafka1:19092,EXTERNAL://${DOCKER_HOST_IP:-192.168.3.163}:9092,DOCKER://host.docker.internal:29092
```

- 在Zoo1和kafka1中都添加了时区，可以解决时间不对的问题

```diff 
+      TZ: Asia/Shanghai
```



```yml
version: '2.1'

services:
  zoo1:
    image: confluentinc/cp-zookeeper:7.3.2
    hostname: zoo1
    container_name: zoo1
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_SERVER_ID: 1
      ZOOKEEPER_SERVERS: zoo1:2888:3888
      TZ: Asia/Shanghai

  kafka1:
    image: confluentinc/cp-kafka:7.3.2
    hostname: kafka1
    container_name: kafka1
    ports:
      - "9092:9092"
      - "29092:29092"
      - "9999:9999"
    environment:
      KAFKA_ADVERTISED_LISTENERS: INTERNAL://kafka1:19092,EXTERNAL://${DOCKER_HOST_IP:-192.168.3.163}:9092,DOCKER://host.docker.internal:29092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT,DOCKER:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: INTERNAL
      KAFKA_ZOOKEEPER_CONNECT: "zoo1:2181"
      KAFKA_BROKER_ID: 1
      KAFKA_LOG4J_LOGGERS: "kafka.controller=INFO,kafka.producer.async.DefaultEventHandler=INFO,state.change.logger=INFO"
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_JMX_PORT: 9999
      KAFKA_JMX_HOSTNAME: ${DOCKER_HOST_IP:-127.0.0.1}
      KAFKA_AUTHORIZER_CLASS_NAME: kafka.security.authorizer.AclAuthorizer
      KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND: "true"
      TZ: Asia/Shanghai
    depends_on:
      - zoo1
```

#### 报错一

调整外部连接地址即可解决问题

```log
[| adminclient-1] org.apache.kafka.clients.NetworkClient : [AdminClient clientId=adminclient-1] Connection to node 1 (/127.0.0.1:9092) could not be established. Broker may not be available.
```

### 如何使用命令行操作集群

```shell
# 创建topic
docker run --rm --network=host \
           confluentinc/cp-kafka:7.3.2 \
           kafka-topics --bootstrap-server=127.0.0.1:9092 \
                        --create \
                        --topic=my-topic \
                        --partitions=3 \
                        --replication-factor=1
# 生产消息
docker run --tty --interactive \
           --rm \
           --network=host \
           confluentinc/cp-kafka:7.3.2 \
           kafka-console-producer --broker-list=127.0.0.1:9092 --topic=my-topic
           
# 消费消息  
docker run --tty --interactive \
           --rm \
           --network=host \
           confluentinc/cp-kafka:7.3.2 \
           kafka-console-consumer --bootstrap-server=127.0.0.1:9092 --topic=my-topic
		   
```

### 如何在Spring Boot中使用Kafka

[参考资料](https://www.baeldung.com/spring-kafka)

[参考仓库代码](https://github.com/Halcyon666/kafka-learn)

仓库代码简单介绍了 Kafka创建字符串消息和Pojo消息，发送消息的示例。

参考资料涉及多种类型消息混合生产和消费。值得学习和借鉴。
