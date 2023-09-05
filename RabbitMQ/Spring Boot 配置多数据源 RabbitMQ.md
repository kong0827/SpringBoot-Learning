# Spring Boot 配置多数据源 RabbitMQ

## 简介

RabbitMQ 是一个基于 AMQP 协议的消息队列系统，它通过消息队列实现了应用程序之间的松耦合，分布式系统中经常用于异步任务处理、解耦、削峰填谷等。

Spring Boot 对 RabbitMQ 进行了良好的封装，提供了一系列便捷的类和方法，通过简单的配置即可使用 RabbitMQ。

本文将介绍如何在 Spring Boot 中配置多数据源的 RabbitMQ，并提供示例代码。

## 依赖引入

在 pom.xml 文件中添加以下依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

## 抽象类

为了实现多数据源配置，我们可以先定义一个抽象类 `AbstractRabbitConfiguration`，其中包含了 RabbitMQ 的一些基本配置信息，如地址、端口、用户名、密码、虚拟主机、队列名、交换机名、确认机制和消费条数等。

```java
@Data
public abstract class AbstractRabbitConfiguration {
     protected String host;
    protected Integer port;
    protected String userName;
    protected String password;
    protected String virtualHost;
    protected String queueName;
    protected String exchangeName;

    protected String routingKey;
    protected String acknowledge = "manual";
    protected Integer prefetch = 1;

    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(Boolean.TRUE);
        return connectionFactory;
    }
}
```

## 子类

在抽象类的基础上，我们可以定义多个子类，用于实现不同数据源的配置。在子类中，我们需要通过 `@ConfigurationProperties` 注解将配置文件中的属性注入到类中，并实现一些必要的 Bean。下面以一个名为 `RabbitConfig` 的子类为例，假设它是用于主数据源配置的。

```java

@Configuration
@ConfigurationProperties(prefix = "kxj.rabbit")
public class RabbitConfig extends AbstractRabbitConfiguration {

    @Bean("primaryConnectionFactory")
    @Primary
    public ConnectionFactory primaryConnectionFactory() {
        return super.connectionFactory();
    }

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(@Qualifier("primaryConnectionFactory") ConnectionFactory connectionFactory,
                                         @Qualifier("confirmCallback") ConfirmCallback confirmCallback,
                                         @Qualifier("returnCallback") ReturnCallback returnCallback) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean(name = "primaryContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("primaryConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 设置ACK确认机制
        factory.setAcknowledgeMode(AcknowledgeMode.valueOf(acknowledge.toUpperCase()));
        // 设置消费者消费条数
        factory.setPrefetchCount(prefetch);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean(name = "primaryRabbitAdmin")
    public RabbitAdmin rabbitAdmin(@Qualifier("primaryConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);

        // 声明交换机，队列及对应绑定关系
        Queue queue = RabbitmqUtil.createQueue(queueName);
        FanoutExchange exchange = RabbitmqUtil.createFanoutExchange(exchangeName);
        Binding binding = RabbitmqUtil.createBinding(queue, exchange, "");
        RabbitmqUtil.createRabbitAdmin(queue, exchange, binding, rabbitAdmin);
        return rabbitAdmin;
    }
}

@Slf4j
@Component
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("传递消息到交换机成功,correlationData:{}, cause:{}", JSON.toJSONString(correlationData), cause);
        } else {
            log.error("传递消息到交换机失败,correlationData:{}, cause:{}", JSON.toJSONString(correlationData), cause);
        }
    }
}

@Slf4j
@Component
public class ReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String msg = new String(message.getBody());
        log.error(String.format("消息{%s}不能被正确路由，routingKey为{%s}", msg, routingKey));
    }
}
```

上述代码中，我们通过 `@ConfigurationProperties` 注解将 `rbox.exception-order.rabbit` 前缀的配置属性注入到 `RabbitConfig` 类中，并实现了 `primaryConnectionFactory`、`primaryRabbitAdmin` 和 `primaryContainerFactory` 三个 Bean。其中，`primaryConnectionFactory` 方法创建了一个主数据源的 `CachingConnectionFactory`，并使用 `@Primary` 注解标记为默认的 Bean。`primaryRabbitAdmin` 方法创建了一个 `RabbitAdmin`，用于声明交换机、队列和绑定关系，并将其设置为自动启动。`primaryContainerFactory` 方法创建了一个 `SimpleRabbitListenerContainerFactory`，用于设置消费者 ACK 确认机制和消费条数。

另外，我们在 `rabbitTemplate` 方法中也进行了一些配置，如设置 `mandatory` 为 `true`，设置消息转换器为 `Jackson2JsonMessageConverter` 等。

## 配置文件

```properties
server.port=8895
kxj.rabbit.host=MQ地址
kxj.rabbit.port=MQ端口
kxj.rabbit.virtualHost=/
kxj.rabbit.userName=guest
kxj.rabbit.password=guest
kxj.rabbit.queueName=test.queue
kxj.rabbit.exchangeName=test.exchange
kxj.rabbit.routingKey=test-routing-key
```





## 工具类

在 RabbitMQ 的配置过程中，我们需要声明交换机、队列和绑定关系等，这些操作可以通过一个工具类 `RabbitmqUtil` 来实现。

```java
public class RabbitmqUtil {

    public static DirectExchange createDirectExchange(String exchangeName) {
        if (StringUtils.isNotBlank(exchangeName)) {
            return new DirectExchange(exchangeName, true, false);
        }
        return null;
    }
    public static TopicExchange createTopicExchange(String exchangeName) {
        if (StringUtils.isNotBlank(exchangeName)) {
            return new TopicExchange(exchangeName, true, false);
        }
        return null;
    }

    public static FanoutExchange createFanoutExchange(String exchangeName) {
        if (StringUtils.isNotBlank(exchangeName)) {
            return new FanoutExchange(exchangeName, true, false);
        }
        return null;
    }

    public static Queue createQueue(String queueName) {
        if (StringUtils.isNotBlank(queueName)) {
            return new Queue(queueName, true);
        }
        return null;
    }

    public static Binding createBinding(Queue queueName, Exchange exchangeName, String routingKeyName) {
        if (Objects.nonNull(queueName) && Objects.nonNull(exchangeName)) {
            return BindingBuilder.bind(queueName).to(exchangeName).with(routingKeyName).noargs();
        }
        return null;
    }

//    public static void createRabbitAdmin(Queue queue, DirectExchange exchange, Binding binding, RabbitAdmin rabbitAdmin) {
//        rabbitAdmin.declareQueue(queue);
//        rabbitAdmin.declareExchange(exchange);
//        rabbitAdmin.declareBinding(binding);
//    }

    public static void createRabbitAdmin(Queue queue, Exchange exchange, Binding binding, RabbitAdmin rabbitAdmin) {
        if (queue != null) {
            rabbitAdmin.declareQueue(queue);
        }
        if (exchange != null) {
            rabbitAdmin.declareExchange(exchange);
        }
        if (binding != null) {
            rabbitAdmin.declareBinding(binding);
        }
    }
}
```





## 测试用例

我们可以编写一些测试用例来验证以上配置是否正确。下面是一个发送消息到主数据源的示例：

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqTest {

    @Autowired
    @Qualifier("primaryRabbitAdmin")
    private RabbitAdmin primaryRabbitAdmin;

    @Autowired
    @Qualifier("primaryContainerFactory")
    private SimpleRabbitListenerContainerFactory primaryContainerFactory;

    @Autowired
    @Qualifier("primaryConnectionFactory")
    private ConnectionFactory primaryConnectionFactory;

    @Autowired
    private RabbitTemplate primaryRabbitTemplate;

    @Test
    public void testSend() {
        String message = "Hello, World!";
        primaryRabbitTemplate.convertAndSend("test.exchange", "test.routingKey", message);
        String receivedMessage = (String) primaryRabbitTemplate.receiveAndConvert("test.queue");
        assertEquals(message, receivedMessage);
    }
}
```

在上面的测试用例中，我们使用了 `@Qualifier` 注解来指定主数据源的 Bean，然后通过 `RabbitTemplate` 发送消息到 `test.exchange`，并在队列 `test.queue` 中接收到消息。我们可以通过断言来判断发送和接收的消息是否一致，以此验证配置是否正确。

![image-20230905001816410](https://kong-blog.oss-cn-shanghai.aliyuncs.com/image-20230905001816410.png)



## 总结

本文介绍了如何在 Spring Boot 中配置多数据源的 RabbitMQ，并提供了示例代码。通过这些配置，我们可以实现不同数据源的 RabbitMQ 发送和接收消息，并且保证了多数据源之间的隔离性和安全性。