## Spring事件发布及监听
### 简介

`Spring` 的事件驱动模型基于 `ApplicationEvent` 和 `ApplicationListener` ，通过事件驱动的方式来实现业务模块之间的交互，交互的方式也有同步和异步两种。事件的发布者仅负责发布事件无需关心事件的接收者，有可能存在一个，也有存在多个接收者。同样，接受者也不知道是谁在发布事件。

Spring的事件驱动模型主要由三部分组成，包括发送消息的生产者，消息，事件监听的消费者，这三者是绑定在一起的，有点类似于RabbitMQ的消息模型。

### 内置事件

`Spring` 提供了内置事件。`Spring` 的核心是 `ApplicationContext`， 当加载 `Bean` 的时候，`ApplicationContext` 会发布某些类型的事件，然后通过 `ApplicationEvent` 和 `ApplicationListener`进行处理。

| 序号 | 事件                  | 描述                                                 |
| ---- | --------------------- | ---------------------------------------------------- |
| 1    | ContextStartedEvent   | 容器启动的时候触发                                   |
| 2    | ContextRefreshedEvent | 容器初始化或刷新*ApplicationContext*时，将发布此事件 |
| 3    | ContextStoppedEvent   | 容器停止的时候触发                                   |
| 4    | ContextClosedEvent    | 容器关闭的时候触发                                   |

####  

### 自定义事件

自定义事件应该

1.  事件应该继承 `ApplicationEvent`

2. 事件的发布者应该注入`ApplicationEventPublisher`

3. 事件监听者应该实现`ApplicationListener`

   



`Spring` 中的事件默认情况下是同步的，生产者发送消息后会阻塞，直到所有的监听器处理完事件