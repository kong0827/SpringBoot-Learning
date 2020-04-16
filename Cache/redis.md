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

