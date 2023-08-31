## MQ

### 定义

面向消息的中间件（message-oriented middleware）MOM能够很好的解决以上问题，是指利用高效可靠的消息传递机制与平台无关的数据交流，并基于数据通信来进行分布式系统的集成。
通过提供消息传递和消息排队模型在分布式环境下提供应用解耦，弹性伸缩，冗余存储、流量削峰，异步通信，数据同步等功能。

大致的过程是这样的：
发送者把消息发送给消息服务器，消息服务器将消息存放在若干队列/主题topic中，在合适的时候，消息服务器回将消息转发给接受者。在这个过程中，发送和接收是异步的，也就是发送无需等待，而且发送者和接受者的生命周期也没有必然的关系；
尤其在发布pub/订阅sub模式下，也可以完成一对多的通信，即让一个消息有多个接受者。

![1587655909336](./src/main/resources/images/1587655909336.png)

### 作用

- 解耦：当新的模块进来时，要做到代码改动最小，能够解耦
- 削峰：设置流量缓冲池，可以让后端系统按照自身吞吐能力进行消费，不被冲垮，能够削峰
- 异步：强弱依赖梳理能够将非关键调用链路的操作异步化并提升整体系统的吞吐能力，能够异步

### 种类

- Kafka
- RabbitMQ
- RocketMQ
- ActiveMQ



### AMQP 和 JMS

MQ是消息通信的模型；实现MQ的大致有两种主流方式：AMQP、JMS。

#### AMQP

AMQP是一种协议，更准确的说是一种binary wire-level protocol（链接协议）。这是其和JMS的本质差别，AMQP不从API层进行限定，而是直接定义网络交换的数据格式。



####  JMS

JMS即Java消息服务（JavaMessage Service）应用程序接口，是一个Java平台中关于面向消息中间件（MOM）的API，用于在两个应用程序之间，或分布式系统中发送消息，进行异步通信。



####  AMQP 与 JMS 区别

- JMS是定义了统一的接口，来对消息操作进行统一；AMQP是通过规定协议来统一数据交互的格式
- JMS限定了必须使用Java语言；AMQP只是协议，不规定实现方式，因此是跨语言的。
- JMS规定了两种消息模式；而AMQP的消息模式更加丰富



### 消息发送和接收机制

所有 MQ 产品从模型抽象上来说都是一样的过程：

消费者(com.kxj.config.consumer)订阅某个队列。生产者(com.kxj.producer)创建消息，然后发布到队列(queue)中，最后将消息发送到监听的消费者

 ![img](./src/main/resources/images/1579252955@f30eb4120b8a37bcc322f9341b83424b.png) 



## RabbitMQ

### 结构图

![1587658012733](./src/main/resources/images/1587658012733.png)

### 执行流程

 ![img](http://www.bjpowernode.com/Public/Uploads/index/itArticle/20200403/1585918217@5eec67072f2a109fa06301cafa2336c2.png)

### 相关概念

- Message

消息，消息是不具名的，它由消息头和消息体组成。消息体是不透明的，而消息头则由一系列的可选属性组成，这些属性包括routing-key(路由键)、priority(相对于其他消息的优先权)、delivery-mode(指出该消息可能需要持久性存储)等。

- Publisher

消息的生产者，也是一个向交换器发布消息的客户端应用程序。

- Exchange

交换器，用来接收生产者发送的消息并将这些消息路由给服务器中的队列。

- Binding

绑定，用于消息队列和交换器之间的关联。一个绑定就是基于路由键将交换

器和消息队列连接起来的路由规则，所以可以将交换器理解成一个由绑定构成的路由表。

- Queue

消息队列，用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一个消息可投入一个或多个队列。消息一直在队列里面，等待消费者连到这个队列将其取走。

- Connection

网络连接，比如一个TCP连接。

- Channel

信道，多路复用连接中的一条独立的双向数据流通道。信道是建立在真实的TCP连接内地虚拟连接，AMQP 命令都是通过信道发出去的，不管是发布消息、订阅队列还是接收消息，这些动作都是通过信道完成。因为对于操作系统来说建立和销毁 TCP 都是非常昂贵的开销，所以引入了信道的概念，以复用一条 TCP连接。

- Consumer

消息的消费者，表示一个从消息队列中取得消息的客户端应用程序。

- Virtual Host

虚拟主机，表示一批交换器、消息队列和相关对象。虚拟主机是共享相同的

身份认证和加密环境的独立服务器域。每个 vhost 本质上就是一个 mini 版的

RabbitMQ 服务器，拥有自己的队列、交换器、绑定和权限机制。vhost 是 AMQP概念的基础，必须在连接时指定，RabbitMQ 默认的 vhost 是 / 。 



### 分发策略

- direct 

  点对点

- fanout

  广播

- topic

  匹配相对路径

  '#' 匹配一个字符

  '*' 匹配多个字符



### 安装

- 下载镜像

  ```shell
  docker pull rabbitmq:tag
  
  # docker pull rabbitmq (镜像未配有控制台)
  # docker pull rabbitmq:management (镜像配有控制台)
  ```

- 启动容器

  ```shell
  docker run --name rabbitmq -d -p 15672:15672 -p 5672:5672 rabbitmq:management
  # 15672是管理界面的端口
  # 5672 是服务的端口
  # RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=password 不配置默认登录界面账号密码都是guest
  ```

   

### 自动配置原理

`RabbitAutoConfiguration`

- 自动配置了 连接工厂 `CachingConnectionFactory`
- `RabbitProperties`   封装了所有配置
- `RabbitTemplate` 给RabbitMQ发送和接收消息
-  `AmqpAdmin`  RabbitMQ系统管理功能组件



![1587650852301](./src/main/resources/images/1587650852301.png)

![1587651012322](./src/main/resources/images/1587651012322.png)

![1587651064042](./src/main/resources/images/1587651064042.png)

![1587651099022](./src/main/resources/images/1587651099022.png)



**序列化**

- 重新定义消息转换器

  ![1587651177505](./src/main/resources/images/1587651177505.png)

  ```java
  @Configuration
  public class RabbitConfig {
  
      @Bean
      public MessageConverter messageConverter() {
          return new Jackson2JsonMessageConverter();
      }
  
  }
  ```




### 入门案例

#### hello world

![img](https://www.rabbitmq.com/img/tutorials/python-one-overall.png)

1. 引入依赖

   ```xml
   <dependency>
       <groupId>com.rabbitmq</groupId>
       <artifactId>amqp-client</artifactId>
   </dependency>
   ```

2. 生产者

   ```java
   public class producer {
   
       private static final String QUEUE_NAME = "hello";
   
       public static void main(String[] args) {
           ConnectionFactory connectionFactory = new ConnectionFactory();
           connectionFactory.setHost("47.102.218.26");
           connectionFactory.setPort(5672);
           try (Connection connection = connectionFactory.newConnection()) {
               Channel channel = connection.createChannel();
               /**
                *
                * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
                *
                * queue：队列名称
                * durable：是否持久化，当mq重启后，消息还在
                * exclusive：
                *      1、是否独占，只能有一个消费者监听这队列
                *      2、当Connection关闭时，是否删除队列
                * autoDelete：是否自动删除。当没有Consumer时，自动删除掉
                * arguments：参数
                */
   
               channel.queueDeclare(QUEUE_NAME, false, false, false, null);
               // 使用默认的交换器，路由键需要设置队列名
               channel.basicPublish("", QUEUE_NAME, null, "hello world".getBytes());
               channel.close();
           } catch (IOException | TimeoutException e) {
               e.printStackTrace();
           }
   
       }
   }
   ```

3. 消费者

   ```java
   public class consumer {
   
       private final static String QUEUE_NAME = "hello";
   
       public static void main(String[] args) throws IOException, TimeoutException {
           ConnectionFactory factory = new ConnectionFactory();
           factory.setHost("47.102.218.26");
           factory.setPort(5672);
   
           Connection connection = factory.newConnection();
           Channel channel = connection.createChannel();
   
           /**
            * 注意
            * Rabbitmq服务通道是持久通道,该queue 已经存在, 而且通道属性需要保持一致
            */
           channel.queueDeclare(QUEUE_NAME, false, false, false, null);
   
   //        DefaultConsumer consumer = new DefaultConsumer(channel) {
   //            /**
   //             *
   //             * @param consumerTag 标识
   //             * @param envelope 获取的一些信息 包括交换机 路由
   //             * @param properties 配置信息
   //             * @param body 数据
   //             * @throws IOException
   //             */
   //            @Override
   //            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
   //                System.out.println("consumerTag:" + consumerTag);
   //                System.out.println("envelope:" + envelope);
   //                System.out.println("properties:" + properties);
   //                System.out.println("message:" + new String(body));
   //            }
   //        };
   //        channel.basicConsume(QUEUE_NAME, true, consumer);
   
           DeliverCallback deliverCallback = (consumerTag, delivery) -> {
               String message = new String(delivery.getBody(), "UTF-8");
               System.out.println(" message: " + message + "'");
           };
           /**
            * 启动一个消费者，并返回服务端生成的消费者标识
            * queue:队列名
            * autoAck：true 接收到传递过来的消息后acknowledged（应答服务器），false 接收到消息后不应答服务器
            * deliverCallback： 当一个消息发送过来后的回调接口
            * cancelCallback：当一个消费者取消订阅时的回调接口;取消消费者订阅队列时除了使用{@link Channel#basicCancel}之外的所有方式都会调用该回调方法
            * @return 服务端生成的消费者标识
            */
           channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
           });
   
       }
   }
   ```

#### work queue

![img](https://www.rabbitmq.com/img/tutorials/python-two.png)

**应用场景**：对于 任务过重或任务较多情况使用工作队列可以提高任务处理的速度

生产者同`hello wolrd`生产者代码

消费者同`hello wolrd`消费者代码，并复制一份

在一个队列中如果有多个消费者，那么消费者之间对于同一个消息的关系是**竞争**的关系



**生产者**

```java
ConnectionFactory connectionFactory = new ConnectionFactory();
connectionFactory.setHost("47.102.218.26");
connectionFactory.setPort(5672);
try (Connection connection = connectionFactory.newConnection()) {
    Channel channel = connection.createChannel();
    /**
     *
     * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
     *
     * queue：队列名称
     * durable：是否持久化，当mq重启后，消息还在
     * exclusive：
     *      1、是否独占，只能有一个消费者监听这队列
     *      2、当Connection关闭时，是否删除队列
     * autoDelete：是否自动删除。当没有Consumer时，自动删除掉
     * arguments：参数
     */

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    // 使用默认的交换器，路由键需要设置队列名
    for (int i = 0; i < 10; i++) {
        channel.basicPublish("", QUEUE_NAME, null, (i+"hello world").getBytes());
    }
    channel.close();
} catch (IOException | TimeoutException e) {
    e.printStackTrace();
}
```



**消费者**

```java
public class consumer01 {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.102.218.26");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" message: " + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

    }
}

public class consumer02 {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.102.218.26");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" message: " + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

    }
}

```



#### pub/sub 订阅模式

![img](https://www.rabbitmq.com/img/tutorials/exchanges.png)

发布订阅模式：
1、每个消费者监听自己的队列。
2、生产者将消息发给broker，由交换机将消息转发到绑定此交换机的每个队列，每个绑定交换机的队列都将接收
到消息



1. ##### 生产者

   ```java
   private final static String FANOUT_QUEUE_1 = "fanout-queue-1";
   private final static String FANOUT_QUEUE_2 = "fanout-queue-2";
   private static final String FANOUT_EXCHANGE = "fanout-exchange";
   
   public static void main(String[] args) {
       ConnectionFactory connectionFactory = new ConnectionFactory();
       connectionFactory.setHost("47.102.218.26");
       connectionFactory.setPort(5672);
       try (Connection connection = connectionFactory.newConnection()) {
           Channel channel = connection.createChannel();
   
           /**
                * 声明交换机
                * exchangeDeclare(String exchange,BuiltinExchangeType type,boolean durable,boolean autoDelete,boolean internal,Map<String, Object> arguments)
                * exchange：交换机名称
                * type：交换机类型
                * durable：是否持久化
                * autoDelete：是否自动删除
                * internal：设置是否是RabbitMQ内部使用，默认false。如果设置为 true ，则表示是内置的交换器，客户端程序无法直接发送消息到这个交换器中，只能通过交换器路由到交换器这种方式
                * arguments：扩展参数，用于扩展AMQP协议自制定化使用
                */
           channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT, true, false, false, null);
   
           // 设置交换机和队列的绑定
           channel.queueDeclare(FANOUT_QUEUE_1, true, false, false, null);
           channel.queueDeclare(FANOUT_QUEUE_2, true, false, false, null);
   
           /**
                * queueBind(String queue, String exchange, String routingKey)
                *
                * 1、queue：队列名称
                * 2、exchange：交换机名称
                * 3、routingKey：路由 fanout 路由设置为空字符转即可
                */
           channel.queueBind(FANOUT_QUEUE_1, FANOUT_EXCHANGE, "");
           channel.queueBind(FANOUT_QUEUE_2, FANOUT_EXCHANGE, "");
   
           // 发送消息
           channel.basicPublish(FANOUT_EXCHANGE, "", null, "hello topic".getBytes());
           /**
             * 即使指定任意路由 也会发送到绑定的队列
             */
           channel.basicPublish(FANOUT_EXCHANGE, "", null, "hello topic".getBytes());
           channel.close();
       } catch (IOException | TimeoutException e) {
           e.printStackTrace();
       }
   }
   ```

2. 消费者同`hello world`消费者，修改监听队列名即可

   

**小结**

交换机需要与队列进行绑定，绑定之后；一个消息可以被多个消费者都收到。

**fanoutExchange 路由设置为空字符串或者设置任意字符串，发送消息时即使指定了路由，也不会生效，会发到绑定的队列中**



**发布订阅模式与工作队列模式的区别**

1、工作队列模式不用定义交换机，而发布/订阅模式需要定义交换机。 

2、发布/订阅模式的生产方是面向交换机发送消息，工作队列模式的生产方是面向队列发送消息(底层使用默认交换机)。

3、发布/订阅模式需要设置队列和交换机的绑定，工作队列模式不需要设置，实际上工作队列模式会将队列绑 定到默认的交换机 



#### routing

![img](https://www.rabbitmq.com/img/tutorials/python-four.png)



- P：生产者，向Exchange发送消息，发送消息时，会指定一个routing key。

- X：Exchange（交换机），接收生产者的消息，然后把消息递交给 与routing key完全匹配的队列

- C1：消费者，其所在队列指定了需要routing key 为 error 的消息

- C2：消费者，其所在队列指定了需要routing key 为 info、error、warning 的消息

  

路由模式特点：

- 队列与交换机的绑定，不能是任意绑定了，而是要指定一个`RoutingKey`（路由key）
- 消息的发送方在 向 Exchange发送消息时，也必须指定消息的 `RoutingKey`。
- Exchange不再把消息交给每一个绑定的队列，而是根据消息的`Routing Key`进行判断，只有队列的`Routingkey`与消息的 `Routing key`完全一致，才会接收到消息



交换机的类型为：Direct，还有队列绑定交换机的时候需要指定routing key。

1. **生产者**

   ```java
   private final static String DIRECT_QUEUE_1 = "direct-queue-1";
   private final static String DIRECT_QUEUE_2 = "direct-queue-2";
   private static final String DIRECT_EXCHANGE = "direct-exchange";
   
   public static void main(String[] args) {
   
       ConnectionFactory connectionFactory = new ConnectionFactory();
       connectionFactory.setHost("47.102.218.26");
       connectionFactory.setPort(5672);
       try (Connection connection = connectionFactory.newConnection()) {
           Channel channel = connection.createChannel();
   
           channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT, true, false, false, null);
   
           channel.queueDeclare(DIRECT_QUEUE_1, true, false, false, null);
           channel.queueDeclare(DIRECT_QUEUE_2, true, false, false, null);
   
           // 绑定交换机和队列，根据路由
           channel.queueBind(DIRECT_QUEUE_1, DIRECT_EXCHANGE, "error");
           channel.queueBind(DIRECT_QUEUE_2, DIRECT_EXCHANGE, "warning");
           channel.queueBind(DIRECT_QUEUE_2, DIRECT_EXCHANGE, "info");
           channel.queueBind(DIRECT_QUEUE_2, DIRECT_EXCHANGE, "error");
   
           // 发送消息
           channel.basicPublish(DIRECT_EXCHANGE, "error", null, "error 级别".getBytes());
           channel.basicPublish(DIRECT_EXCHANGE, "info", null, "info 级别".getBytes());
           channel.close();
       } catch (IOException | TimeoutException e) {
           e.printStackTrace();
       }
   }
   ```

   

2. **消费者**

   - 消费者1

     ```java
     public class consumer01 {
         private final static String FANOUT_QUEUE_1 = "direct-queue-1";
     
         public static void main(String[] args) throws Exception {
             ConnectionFactory factory = new ConnectionFactory();
             factory.setHost("47.102.218.26");
             factory.setPort(5672);
     
             Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
     
             channel.queueDeclare(FANOUT_QUEUE_1, true, false, false, null);
     
             DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                 String message = new String(delivery.getBody(), "UTF-8");
                 System.out.println(" message: " + message + "'");
             };
             channel.basicConsume(FANOUT_QUEUE_1, true, deliverCallback, consumerTag -> {
             });
     
         }
     }
     ```

   - 消费者2

     ```java
     public class consumer02 {
         private final static String FANOUT_QUEUE_2 = "direct-queue-2";
     
         public static void main(String[] args) throws Exception {
             ConnectionFactory factory = new ConnectionFactory();
             factory.setHost("47.102.218.26");
             factory.setPort(5672);
     
             Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
     
             channel.queueDeclare(FANOUT_QUEUE_2, true, false, false, null);
     
             DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                 String message = new String(delivery.getBody(), "UTF-8");
                 System.out.println(" message: " + message + "'");
             };
             channel.basicConsume(FANOUT_QUEUE_2, true, deliverCallback, consumerTag -> {
             });
         }
     }
     
     ```

   **小结**

   ​	Routing模式要求队列在绑定交换机时要指定routing key，消息会转发到符合routing key的队列。



#### topics

![img](https://www.rabbitmq.com/img/tutorials/python-five.png)

`Topic`类型与`Direct`相比，都是可以根据`RoutingKey`把消息路由到不同的队列。只不过`Topic`类型`Exchange`可以让队列在绑定`Routing key` 的时候**使用通配符**！



`Routingkey` 一般都是有一个或多个单词组成，多个单词之间以”.”分割，例如： `item.insert`

 通配符规则：

`#`：匹配一个或多个词

`*`：匹配不多不少恰好1个词

举例：

`item.#`：能够匹配`item.insert.abc` 或者 `item.insert`

`item.*`：只能匹配`item.insert`



**生产者**

```java
public class producer {

    private final static String TOPIC_QUEUE_1 = "topic-queue-1";
    private final static String TOPIC_QUEUE_2 = "topic-queue-2";
    private static final String TOPIC_EXCHANGE = "topic-exchange";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("47.102.218.26");
        connectionFactory.setPort(5672);
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC, true, false, false, null);

            channel.queueDeclare(TOPIC_QUEUE_1, true, false, false, null);
            channel.queueDeclare(TOPIC_QUEUE_2, true, false, false, null);

            // 绑定交换机和队列，根据路由
            channel.queueBind(TOPIC_QUEUE_1, TOPIC_EXCHANGE, "*.orange.*");
            channel.queueBind(TOPIC_QUEUE_1, TOPIC_EXCHANGE, "lazy.*");
            channel.queueBind(TOPIC_QUEUE_2, TOPIC_EXCHANGE, "*.*.rabbit");
            channel.queueBind(TOPIC_QUEUE_2, TOPIC_EXCHANGE, "lazy.#");

            // 发送消息
            channel.basicPublish(TOPIC_EXCHANGE, "fruit.orange.weight", null, "*.orange.*".getBytes());
            channel.basicPublish(TOPIC_EXCHANGE, "user.info.rabbit", null, "*.*.rabbit".getBytes());
            channel.basicPublish(TOPIC_EXCHANGE, "lazy.rabbit", null, "lazy.# / lazy.*".getBytes());
            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}

```



**消费者**

```java
public class consumer01 {
    private final static String TOPIC_QUEUE_1 = "topic-queue-1";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.102.218.26");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TOPIC_QUEUE_1, true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" message: " + message + "'");
        };
        channel.basicConsume(TOPIC_QUEUE_1, true, deliverCallback, consumerTag -> {
        });

    }
}

public class consumer02 {
    private final static String TOPIC_QUEUE_2 = "topic-queue-2";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.102.218.26");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TOPIC_QUEUE_2, true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" message: " + message + "'");
        };
        channel.basicConsume(TOPIC_QUEUE_2, true, deliverCallback, consumerTag -> {
        });

    }
}
```



**小结**

Topic主题模式可以实现 `Publish/Subscribe发布与订阅模式` 和 ` Routing路由模式` 的功能；只是Topic在配置routing key 的时候可以使用通配符，显得更加灵活







## Spring Boot整合 RabbitMQ



**生产者工程：**

1. application.yml文件配置RabbitMQ相关信息；
2. 在生产者工程中编写配置类，用于创建交换机和队列，并进行绑定

3. 注入RabbitTemplate对象，通过RabbitTemplate对象发送消息到交换机



**消费者工程：**

1. application.yml文件配置RabbitMQ相关信息
2. 创建消息处理类，用于接收队列中的消息并进行处理



### 生产者

1. 添加依赖

   ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
   </dependency>
   ```

2. 启动类

   ```java
   @SpringBootApplication
   public class ProducerApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(ProducerApplication.class, args);
       }
   }
   ```

3. 配置文件

   ```yml
   server:
     port: 8894
   spring:
     rabbitmq:
       host: 47.102.218.26
       port: 5672
       username: guest
       password: guest
       virtual-host: /
   ```

4. 绑定交换机和队列

   ```java
   @Configuration
   public class TopicConfig {
   
       public static final String SPRING_BOOT_QUEUE = "spring-boot-queue";
       public static final String SPRING_BOOT_EXCHANGE = "spring-boot-exchange";
   
       /**
        * 1、定义交换机
        * 2、定义队列
        * 3、定义队列和交换机的绑定
        */
   
       @Bean
       public Exchange exchange() {
           return ExchangeBuilder.topicExchange(SPRING_BOOT_EXCHANGE).build();
       }
   
       @Bean
       public Queue queue() {
           return QueueBuilder.durable(SPRING_BOOT_QUEUE).build();
       }
   
       @Bean
       public Binding binding(@Qualifier(value = "exchange") Exchange exchange, @Qualifier(value = "queue") Queue queue) {
           return BindingBuilder.bind(queue).to(exchange).with("#.user").noargs();
       }
   }
   ```

5. 发送消息

   ```java
   @Autowired
       RabbitTemplate rabbitTemplate;
   
       public void producer() {
           rabbitTemplate.convertAndSend(TopicConfig.SPRING_BOOT_EXCHANGE, "kxj.user", "hello rabbitmq");
       }
   ```

   

### 消费者

@RabbitListener 和 @RabbitHandler 搭配使用

- @RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用
- @RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型

```java
@Component
@RabbitListener(queues = "consumer_queue")
public class Receiver {

    @RabbitHandler
    public void processMessage1(String message) {
        System.out.println(message);
    }

    @RabbitHandler
    public void processMessage2(byte[] message) {
        System.out.println(new String(message));
    }
    
}
```

1. 添加依赖

   ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
   </dependency>
   ```

2. 启动类

   ```java
   @SpringBootApplication
   public class ConsumerApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(ConsumerApplication.class, args);
       }
   }
   ```

3. 配置文件

   ```yml
   server:
     port: 8895
   spring:
     rabbitmq:
       host: 47.102.218.26
       port: 5672
       username: guest
       password: guest
       virtual-host: /
   ```

4. 消息监听类

   ```java
   @Component
   public class TopicConsumer {
   
       @RabbitListener(queues = "spring-boot-queue")
       public void listener(Message message) {
           System.out.println(message);
       }
   }
   ```

   

### RabbitMQ 自动配置





### 消息的可靠投递

rabbitmq 整个消息投递的路径为：
**producer--->rabbitmq broker--->exchange--->queue--->consumer**
**消息从 producer 到 exchange 则会返回一个 confirmCallback **
**消息从 exchange-->queue 投递失败则会返回一个 returnCallback **

**配置文件**

```yml
server:
  port: 8894
spring:
  rabbitmq:
    host: 47.102.218.26
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    
    # 老版本
    #开启消息发送确认机制，默认为false
    #如果没有本条配置信息，当消费者收到生产者发送的消息后，生产者无法收到确认成功的回调信息
    publisher-confirms: true
    #支持消息发送失败返回队列,默认为false
    publisher-returns: true
    
    # 新版本
     #    对于ReturnCallback来说：
    #    exchange到queue成功,则不回调return
    #    exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
    #    比如路由不到队列时触发回调
    publisher-returns: true
    #    对于ConfirmCallback来说：
    #    如果消息没有到exchange,则confirm回调,ack=false
    #    如果消息到达exchange,则confirm回调,ack=true
    publisher-confirm-type: correlated
```



```java
@Configuration
public class RabbitMQConfig {


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         @Qualifier("confirmCallback") ConfirmCallback confirmCallback,
                                         @Qualifier("returnCallback") ReturnCallback returnCallback) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        // 设置交换机处理消息的模式 要想使 returnCallback 生效，必须设置为true
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange("exchange-7").durable(true).build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable("queue-7").build();
    }

    @Bean
    public Binding binding(@Qualifier("exchange") Exchange exchange, @Qualifier("queue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("test").noargs();
    }

}

```

**confirm 确认模式**

```java
@Component
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("传递消息到交换机成功");
        } else {
            System.out.println("传递消息到交换机失败");
        }
    }
}

```

**return 退回模式**

```java 
@Component
public class ReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String msg = new String(message.getBody());
        System.out.println(String.format("消息{%s}不能被正确路由，routingKey为{%s}", msg, routingKey));
    }
}
```

**总结**

- 设置ConnectionFactory的publisher-confirms="true" 开启 确认模式。
- 使用rabbitTemplate.setConfirmCallback设置回调函数。当消息发送到exchange后回调confirm方法。在方法中判断ack，如果为true，则发送成功，如果为false，则发送失败，需要处理。
- 设置ConnectionFactory的publisher-returns="true" 开启 退回模式。
- 使用rabbitTemplate.setReturnCallback设置退回函数，当消息从exchange路由到queue失败后，如果设置了rabbitTemplate.setMandatory(true)参数，则会将消息退回给producer。并执行回调函数returnedMessage。

### ACK

步骤

1. 设置手动签收 `acknowledge = "manual"`
2. 监听方法添加`Channel`参数
3. 如果消息处理成功，则调用`channel`的`basicAck()`签收
4. 如果消息处理失败，则调用`channel`的`basicNack()`拒绝签收，`broker`重新发送给`Consumer`

配置文件

**配置文件**

```yml
server:
  port: 8894
spring:
  rabbitmq:
    host: 47.102.218.26
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    listener:
      simple:
        acknowledge-mode: manual
      direct:
        acknowledge-mode: manual
```

**配置类**

```java
@Configuration
public class RabbitMQConfig {

    public static final String AKC_TOPIC_EXCHANGE = "ack-topic-exchange";
    public static final String ACK_TOPIC_QUEUE = "ack-topic-queue";
    public static final String ACK_TOPIC = "topic-ack.#";

    public static final String ACK_DIRECT_EXCHANGE = "ack-direct-exchange";
    public static final String ACK_DIRECT_QUEUE = "ack-direct-queue";
    public static final String ACK_DIRECT = "direct-ack";

    @Bean
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange(AKC_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue topicQueue() {
        return QueueBuilder.durable(ACK_TOPIC_QUEUE).build();
    }

    @Bean
    public Binding topicBinding(@Qualifier(value = "topicExchange") Exchange exchange, @Qualifier(value = "topicQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(ACK_TOPIC).noargs();
    }

    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange(ACK_DIRECT_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(ACK_DIRECT_QUEUE).build();
    }

    @Bean
    public Binding directBinding(@Qualifier(value = "directExchange") Exchange exchange, @Qualifier(value = "directQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(ACK_DIRECT).noargs();
    }

}
```

**生产者**

```java
@Component
public class RabbitProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void topicProducer() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.AKC_TOPIC_EXCHANGE, "topic-ack.test", "hello topic");
    }

    public void directProducer() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.ACK_DIRECT_EXCHANGE, RabbitMQConfig.ACK_DIRECT, "hello direct");
    }
}
```

**消费者**

```java
@Component
public class RabbitMQListener {

    @RabbitListener(queues = RabbitMQConfig.ACK_DIRECT_QUEUE)
    public void consumer(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(new String(message.getBody()));
            System.out.println("业务逻辑1....");
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, true);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.ACK_TOPIC_QUEUE)
    public void consumer2(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(new String(message.getBody()));
            System.out.println("业务逻辑2....");
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
```

**总结**

忘记通过basicAck返回确认信息是常见的错误。这个错误非常严重，将导致消费者客户端退出或者关闭后，消息会被退回RabbitMQ服务器，这会使RabbitMQ服务器内存爆满，而且RabbitMQ也不会主动删除这些被退回的消息。只要程序还在运行，没确认的消息就一直是 Unacked 状态，无法被 RabbitMQ 重新投递。更厉害的是，RabbitMQ 消息消费并没有超时机制，也就是说，程序不重启，消息就永远是 Unacked 状态。处理运维事件时不要忘了这些 Unacked 状态的消息。当程序关闭时（实际只要 消费者 关闭就行），消息会恢复为 Ready 状态



### 并发与限流

https://blog.csdn.net/linsongbin1/article/details/100658415

#### 并发

一个listener对应多个consumer
默认情况一下，一个listener对应一个consumer，如果想对应多个，有两种方式。

方式一：直接在application.yml文件中配置

```yml
spring:
  rabbitmq:
    listener:
      simple:
        # 消费者最小数量 消费端的监听个数(即@RabbitListener开启几个线程去处理数据。)
        concurrency: 5
        # 消费者最大数量
        max-concurrency: 5
```

这个是个全局配置，应用里的任何队列对应的listener都至少有5个consumer，但是千万别这么做，因为一般情况下，一个listener对应一个consumer是够用的。只是针对部分场景，才需要一对多。

 

方式二：直接在@RabbitListener上配置

```java
@Component
public class SpringBootMsqConsumer {
    @RabbitListener(queues = "spring-boot-direct-queue",concurrency = "5-10")
    public void receive(Message message) {
        System.out.println("receive message:" + new String(message.getBody()));
    }
}

```

利用@RabbitListener中的concurrency属性进行指定就行。例如上面的

concurrency = “5-10”

就表示最小5个，最大10个consumer。启动消费端应用后，找到spring-boot-direct-queue这个队列的consumer，会发现有5个

![image-20210316002925783](E:%5CgithubResp%5CSpringBoot-Demo%5CRabbitMQ%5Cdoc%5Cimage-20210316002925783.png)

![image-20210316003120886](E:%5CgithubResp%5CSpringBoot-Demo%5CRabbitMQ%5Cdoc%5Cimage-20210316003120886.png)



#### **限流机制**

1. 确保为ack机制为手动确认

2. `listener-container`配置属性 `perfetch = 1`

   prefetch = 1, 表示消费端一次从mq中拿取一条数据消费，知道手动确认消费完毕后， 才会继续拉取下一条消息

**全局配置**

```yml
spring:
  rabbitmq:
    host: 47.102.218.26
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    listener:
      simple:
        acknowledge-mode: manual
        # 在单个请求中处理的消息个数，他应该大于等于事务数量(unack的最大数量)
        prefetch: 10
      direct:
        prefetch: 10
```

**特定消费者配置**

```java
@Component
public class SpringBootMsqConsumer {
    @RabbitListener(queues = "spring-boot-direct-queue",concurrency = "5-10",containerFactory = "consumerListenerContainer")
    public void receive(Message message) {
        System.out.println("receive message:" + new String(message.getBody()));
    }
}

@Configuration
public class RabbitMqConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean(name = "consumerListenerContainer")
    public SimpleRabbitListenerContainerFactory consumerListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(50);
        return factory;
    }
}
```



### TTL

- 队列统一过期

  ```java
  @Bean
  public Queue queue() {
      Map<String, Object> map = new HashMap<>(10);
      // 队列中的消息未被消费则30秒后过期
      map.put("x-message-ttl", 30000);
      return QueueBuilder.durable(TTL_QUEUE).withArguments(map).build();
  }
  ```

- 消息单独过期

  ```java
   /**
     * 消息单独过期
     */
  public void producer2() {
      MessageProperties messageProperties = new MessageProperties();
      messageProperties.setExpiration("10000");
      Message message = new Message("hello rabbitmq".getBytes(StandardCharsets.UTF_8), messageProperties);
      rabbitTemplate.convertAndSend(RabbitConfig.TTL_EXCHANGE, "kxj.user", message);
  }
  
  /**
    * 消息单独过期
    */
  public void producer3() {
      MessagePostProcessor messagePostProcessor = message -> {
          message.getMessageProperties().setExpiration("20000");
          return message;
      };
      rabbitTemplate.convertAndSend(RabbitConfig.TTL_EXCHANGE, "kxj.user", "hello rabbitmq", messagePostProcessor);
  }
  ```

  



**小结**

- 如果设置了消息的过期时间，也设置了消息过期时间，以时间短的为准
- 设置队列过期时间使用参数：x-message-ttl，单位：ms(毫秒)。会对整个队列消息统一过期
- 设置消息过期时间使用参数：expiration，单位：ms(毫秒)，当消息在队列头部时（消费时），会单独判断这一消息是否过期



### 死信队列

Dead-Letter-Exchange

消息成为死信的三种情况 

1. 队列消息长度到达限制
2. 消费者拒绝接收消费信息，basicNack/basicReject, 并且不把消息重新放入原目标队列，requeue=false
3. 原队列存在消息过期设置，消息到达超时时间未被消费

队列绑定死信交换机

给队列设置参数：**x-dead-letter-exchange** 和 **x-dead-letter-routing-key**



RabbitMQ连接信息配置

```yml
server:
  port: 8894
spring:
  rabbitmq:
    host: 47.102.218.26
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    listener:
      simple:
        retry:
          enabled: true # 允许消息消费失败的重试
          max-attempts: 3 # 消息最多消费次数3次
          initial-interval: 1000 # 消息多次消费的间隔1秒
        default-requeue-rejected: false #  设置为false，会丢弃消息或者重新发布到死信队列
```

业务队列配置类

```java
package com.kxj.config;

import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kxj
 * @date 2021/3/20 21:46
 * @desc
 */
@Configuration
public class RabbitConfig {
    public static final String COMMON_EXCHANGE = "common_exchange";//消息接收的交换机
    public static final String COMMON_ROUTING_KEY = "common_key";//消息routing key
    public static final String COMMON_QUEUE = "common_queue"; //消息存储queue

    public static final String DLX_EXCHANGE = "dlx_exchange";//重定向交换机
    public static final String DLX_ROUTING_KEY = "dlx_key"; //重定向队列routing key
    public static final String DLX_QUEUE = "dlx_queue"; //重定向消息存储queue

    /**
     * 正常交换机的定义
     * @return
     */
    @Bean
    public Exchange commonExchange() {
        return ExchangeBuilder.directExchange(COMMON_EXCHANGE).build();
    }

    @Bean
    public Queue commonQueue() {
        Map<String, Object> args = new HashMap<>(8);

        /**
         * 消息成为死信后重定向到死信交换机
         */
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);


        // 设置超时时间
        args.put("x-message-ttl", 10000);
        // 设置队列的最大长度
        args.put("x-max-length", 10);
        return QueueBuilder.durable(COMMON_QUEUE).withArguments(args).build();
    }

    @Bean
    public Binding commonBinding() {
        return BindingBuilder.bind(commonQueue()).to(commonExchange()).with(COMMON_ROUTING_KEY).noargs();
    }

    /**
     * 定义死信交换机
     */
    @Bean
    public Exchange dlxExchange() {
        return ExchangeBuilder.directExchange(DLX_EXCHANGE).build();
    }

    /**
     * 私信队列
     * @return
     */
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(DLX_QUEUE).build();
    }

    /**
     * 死信交换机和队列的绑定
     */
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DLX_ROUTING_KEY).noargs();
    }


}
```

业务队列生产者

```java
@Component
public class RabbitProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void producer(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.COMMON_EXCHANGE, RabbitConfig.COMMON_ROUTING_KEY, message);
    }

}
```

业务消费者

```java
@Component
@org.springframework.amqp.rabbit.annotation.RabbitListener(queues = RabbitConfig.COMMON_QUEUE)
public class RabbitListener {

    @RabbitHandler
    public void listener(String message) {
        System.out.println(message);
        int i = 1 / 0;
    }
}
```

测试类

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Autowired
    RabbitProducer rabbitProducer;

    /**
     * 超时
     */
    @Test
    public void test() {
        rabbitProducer.producer("hello 死信队列");
    }

    /**
     * 队列消息长度到达限制
     */
    @Test
    public void test2() {
        for (int i = 0; i < 30; i++) {

            rabbitProducer.producer("hello 死信队列");
        }
    }

    /**
     * 消息拒收
     */
    @Test
    public void test3() {
        rabbitProducer.producer("hello 消费者拒收");
    }
}
```



### 延迟队列

延迟队列，即消息进入队列不会立即被消费，只有达到指定时间后，才会被消费

#### TTL + 死信队列

- 代码大致同死信队列

- rabbitmq-delayed-message-exchange

  在rabbitmq 3.5.7及以上的版本提供了一个插件（rabbitmq-delayed-message-exchange）来实现延迟队列功能。同时插件依赖Erlang/OPT 18.0及以上

  `yml`配置

  ```yml
  server:
    port: 8895
  spring:
    rabbitmq:
      host: 47.102.218.26
      port: 5672
      username: guest
      password: guest
  ```

  `RabbitMq` 配置类

  ```java
  @Configuration
  public class QueueConfig {
  
      /**
       * 使用的是CustomExchange,不是DirectExchange，另外CustomExchange的类型必须是x-delayed-message。
       * @return
       */
      @Bean
      public CustomExchange delayExchange() {
          Map<String, Object> args = new HashMap<>();
          args.put("x-delayed-type", "direct");
          return new CustomExchange("test_exchange", "x-delayed-message",true, false,args);
      }
  
      @Bean
      public Queue queue() {
          return new Queue("test_queue_1", true);
      }
  
      @Bean
      public Binding binding() {
          return BindingBuilder.bind(queue()).to(delayExchange()).with("test_queue_1").noargs();
      }
  }
  ```

  生产者

  ```java
  @Component
  public class MessageSender {
  
      @Autowired
      RabbitTemplate rabbitTemplate;
  
      /**
       * 注意在发送的时候，必须加上一个header
       *
       * x-delay
       * @param queueName
       * @param msg
       */
      public void sendMessage(String queueName, String msg) {
          System.out.println("消息发送时间："+ LocalDateTime.now());
          rabbitTemplate.convertAndSend("test_exchange", queueName, msg, message -> {
              message.getMessageProperties().setHeader("x-delay",30000);
              return message;
          });
      }
  }
  
  ```

  消费者

  ```java
  @Component
  public class MessageConsumer {
  
      @RabbitListener(queues = "test_queue_1")
      public void receive(String msg) {
          System.out.println("消息接收时间："+ LocalDateTime.now());
          System.out.println("接收的消息：" + msg);
      }
  }
  ```

  

  

![image-20210401001014021](E:%5CgithubResp%5CSpringBoot-Demo%5CMQ%5Csrc%5Cmain%5Cresources%5Cimages%5Capplication.yml)

![image-20210401001159006](E:%5CgithubResp%5CSpringBoot-Demo%5CMQ%5Csrc%5Cmain%5Cresources%5Cimages%5Cimage-20210401001159006.png)

### 发布者确认模式 异步监听

### 发布者确认模式 批量确认

### 延迟队列





### 生产注意点

1、Exchange 和 queue 是多对多的关系，即一个 Exchange 可以发送消息到多个队列，一个队列也可以接受多个 exchange 发送的消息，推荐在实际生产环境中，一个 Exchange 对应多个队列，尽量不使用多个 Exchange 对一个队列。

在实际的生产时，不推荐在代码中声明 exchange、queue 以及 exchange 和 queue的绑定关系，最好是在控制台就做好这些声明，在代码中直接使用。如果非要在代码中声明，一定要在生产者端和消费者端同时声明，以免启动时报错（找不到 exchange 和 queue 而报错）。

2、**在使用队列之前，必须先声明它**。如果队列不存在，则声明队列将导致它被创建。如果队列已经存在并且其属性与声明中的属性相同，则声明将不起作用。**当现有队列属性与声明中的属性不同时**，将引发代码为 406 ( PRECONDITION_FAILED )的通道级异常。







