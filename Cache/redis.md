### Redis的发布与订阅

#### 创建一个Redis的消息接收器

```java
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
        counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }
}
```

#### 注册监听器并发送消息

Spring Data Redis提供了使用Redis发送和接收消息所需的所有组件。具体来说，您需要配置：

- 连接工厂
- 消息侦听器容器
- Redis模板

可以使用Redis模板发送消息，并在`Receive`消息监听器中注册，以便接收消息。连接工厂将同时注册驱动模板和消息侦听器容器，从而使它们连接到Redis服务器

`RedisConnectionFactory`继承`JedisConnectionFactory`.连接工厂被注入到消息侦听器容器和Redis模板

```java
 @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

```

`listenerAdapter`方法中定义的Bean在中定义的消息侦听器容器中注册为消息侦听器`container`，并将侦听有关该`chat`主题的消息。由于`Receiver`该类是POJO，因此需要将其包装在实现该`MessageListener`接口的消息侦听器适配器中（要求`addMessageListener()`）。消息侦听器适配器还配置为在消息到达时调用该`receiveMessage()`方法`Receiver`。

连接工厂和消息侦听器容器bean就是您用来侦听消息的全部。要发送消息，您还需要一个Redis模板。在这里，它是一个配置为的bean `StringRedisTemplate`，其实现`RedisTemplate`着重于Redis的常用用法，其中键和值都是`String`实例。

#### 测试

```java
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ReceiverMessageTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverMessageTest.class);

    @Autowired
    Receiver receiver = new Receiver();

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() throws InterruptedException {
        while (receiver.getCount() == 0) {
            try {
                LOGGER.info("Sending message...");
                stringRedisTemplate.convertAndSend("chat", "Hello from Redis!");

                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```



### Redis 主从复制

核心：解决数据的冗余备份，不能解决故障的自动转移

从节点只能执行读操作，不能执行写操作

#### 下载配置文件

```shell
wget http://download.redis.io/redis-stable/redis.conf
```

#### 修改配置文件信息

```shell
#// 开启远程连接，已踩坑
#bind 127.0.0.1      需注释
protected-mode no   
#// 持久化
appendonly yes
#// 设置密码
requirepass password
```

#### 启动容器

```shell
docker run -d -p 6379:6379 --name master-redis
-v /root/redis/redis.conf:/etc/redis/redis.conf 
-v /root/redis/data:/data 
redis:latest redis-server 
/etc/redis/redis.conf
```

命令解析：

- `-v /root/redis/redis.conf:/etc/redis/redis.conf `

  `-v /root/redis/data:/data `

  把本地的配置文件映射到镜像，修改本地文件，相当于直接修改镜像文件。更改后可以使用`docker restart 容器id/容器名` 重启服务

- 配置文件中持久化和设置密码也可以在启动容器中设置

  ```shell
  --appendonly yes
  --requirepass password
  ```

  

  配置master-redis必须打开持久化，否则master服务挂了之后，从服务仍然可以获取到数据。但是master服务重启后，由于没有持久化，数据会全部清空。从服务会立即同步主服务的数据，从而造成数据丢失

#### 配置从服务(slave-redis)

1. 复制一份redis.conf的文件

   ```shell
   mv redis.conf redis-salve-1.config
   ```

2. 启动容器

   ```shell
   docker run -d -p 6380:6379 --name salve-redis
   -v /root/redis/redis.conf:/etc/redis/redis-salve-1.config
   redis:latest redis-server 
   /etc/redis/redis.conf
   --appendonly yes
   ```

3. 查看两个服务器的IP

   ```shell
   docker inspect 容器ID/容器名
   ```

   ![1587052895139](E:\githubResp\SpringBoot-Demo\Cache\src\main\resources\images\1587052895139.png)

4. 配置从服务器

   进入docker容器内部

   ```shell
   docker exec -it 容器ID/容器名 redis-cli
   ```

   如果redis设置密码，需验证密码

   ```shell
   auth 密码
   ```

   查看当前redis角色

   ```shell
   info replication
   ```

   ![1587053397739](E:\githubResp\SpringBoot-Demo\Cache\src\main\resources\images\1587053397739.png)

​     **配置**

```she
slaveof master-redis-IP master-redis-port
```

​	如果master-redis设置了密码

```shell
config set masterauth master-password
```

#### 测试

master-redis设值，salve-master会同步更新

![1587053820188](E:\githubResp\SpringBoot-Demo\Cache\src\main\resources\images\1587053820188.png)

salve-redis只能执行读操作，不能写

![1587053873453](E:\githubResp\SpringBoot-Demo\Cache\src\main\resources\images\1587053873453.png)



### 哨兵机制

解决问题：

- **带有自动的处理故障转移功能的主从式架构**
- 自动完成数据冗余备份

缺点：

- 无法解决现有系统单节点并发压力和物理上限问题





