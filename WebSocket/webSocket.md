## WebSocket

### 简介

`websocket`类似于`http`，也是一种通信协议，他在服务器和客户端提供了双向的全双工通信通道。一旦在客户端和服务端建立了`WebSocket`连接，双方就可以无休止的交换信息，知道任何一方关闭连接为止。

以下是HTTP的一些缺点，由于这些缺点，它们不适用于某些情况-

 ![boot-32_4](https://www.javainuse.com/boot-32_4.jpg) 

- **传统的HTTP请求是单向的-** 在传统的客户端服务器通信中，客户端始终会发起请求。
- **半双工-** 用户请求资源，然后服务器将其提供给客户端。响应仅在请求之后发送。因此，一次只发生一个请求。
- **多个TCP连接-** 对于每个请求，都需要建立一个新的**TCP**会话，然后在收到响应后将其关闭。因此，如果不使用WebSockets，我们将有多个会话。
- **大量-** 正常的HTTP请求和响应要求客户端和服务器之间交换额外的数据。

 WebSocket是一种计算机通信协议，可通过单个TCP连接提供全双工通信通道。 

 ![boot-32_5](https://www.javainuse.com/boot-32_5.jpg) 

- **WebSocket是双向的-**使用WebSocket，客户端或服务器均可启动发送消息。
- **WebSocket是全双工的-**客户端和服务器之间的通信彼此独立。
- **单个TCP连接-**初始连接使用HTTP，然后此连接升级为基于套接字的连接。然后，此单个连接将用于以后的所有通信
- **轻巧-**与http相比，WebSocket消息数据交换轻得多。





### 入门案例

#### 1、导入依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-websocket</artifactId>
    <version>5.2.2.RELEASE</version>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-messaging</artifactId>
    <version>5.2.2.RELEASE</version>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.10.2</version>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId> 
    <version>2.10.2</version>
</dependency>
```



#### 2、在Spring中启动webSocket

```java
@Configuration
//启用由消息代理支持的WebSocket消息处理
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 该registerStompEndpoints()方法注册/gs-guide-websocket端点，启用SockJS后备选项，
     * 以便在WebSocket不可用时可以使用备用传输。
     * SockJS客户端将尝试连接/gs-guide-websocket并使用最佳的可用传输方式（websocket，xhr-streaming，xhr-polling等）
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

    /**
     * configureMessageBroker实现WebSocketMessageBrokerConfigurer用于配置消息代理的默认方法。
     * 它首先通过调用enableSimpleBroker()来启用一个简单的基于内存的消息代理，以将问候消息在前缀为的目的地携带回客户端/topic
     * 它还/app为绑定到的方法指定的消息的前缀@MessageMapping。此前缀将用于定义所有消息映射。
     * 例如，/app/hello是GreetingController.greeting()方法映射到处理的端点。
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}

```

