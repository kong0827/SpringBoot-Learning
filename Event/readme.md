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







### 自定义事件

创建/监听事件应该以下准则

-  事件类应该继承 `ApplicationEvent`
- 事件的发布者应该注入`ApplicationEventPublisher`
- 事件监听者应该实现`ApplicationListener`



1. 创建事件类 继承 `ApplicationEvent`

   ```java
   public class CustomSpringEvent extends ApplicationEvent {
   
       private String message;
   
       public CustomSpringEvent(Object source, String message) {
           super(source);
           this.message = message;
       }
   
       public String getMessage() {
           return message;
       }
   }
   ```

   

2. 创建事件的发布者 注入`ApplicationEventPublisher`

   ```java
   @Component
   public class CustomSpringEventPublisher {
      
       @Autowired
       ApplicationEventPublisher applicationEventPublisher;
   
       public void publishCustomEvent(String message) {
           CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, message);
           // 发布事件
           applicationEventPublisher.publishEvent(customSpringEvent);
       }
   }
   ```

3. 创建事件的监听者 实现ApplicationListener接口

   ```java
   @Component
   public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {
   
       @Override
       public void onApplicationEvent(CustomSpringEvent event) {
           System.out.println("接收到的事件：" + event.getMessage());
       }
   }
   ```



### 注解驱动

`Spring 4.1`后提供了 `@EventLister` , 不需要手动实现 `ApplicationListener`接口实现事件的监听，同时也可以配置`@Async` 使用

```java
public @interface EventListener {

	@AliasFor("classes")
	Class<?>[] value() default {};

	@AliasFor("value")
	Class<?>[] classes() default {};

	String condition() default "";

}
```

- value: classes别名
- classes： 可以指定监听的消息对象类型
- condition：指定条件下触发事件监听, 当表达式计算结果为true时才触发



**事件发布**

```java
@Component
public class ApplicationEventPublisher {

    // ApplicationContext 本身可以发布各种事件
    @Autowired
    ApplicationContext applicationContext;

    public void publish(String message) {
        applicationContext.publishEvent(message);
    }
}
```

**事件监听**

```java
@Component
public class AsyncApplicationEventListener {

    @EventListener(String.class)
    public void listener(String message) throws InterruptedException {
        System.out.println("1-异步事件监听, 消息为：" + message);
    }

    /**
     * 可以不指定classes，默认监听的是方法参数中的事件
     * @param message
     * @throws InterruptedException
     */
    @EventListener(condition = "#message.length() > 5")
    public void listener2(String message) throws InterruptedException {
        System.out.println("2-异步事件监听, 消息为：" + message);
    }

    /**
     * 当message长度大于50的时候才会触发
     * @param message
     * @throws InterruptedException
     */
    @EventListener(condition = "#message.length() > 50")
    public void listener3(String message) throws InterruptedException {
        System.out.println("3-异步事件监听, 消息为：" + message);
    }
}
```

**测试**

```java
@SpringBootTest(classes = EventApplication.class)
public class AsyncApplicationEventPublisherTest {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void publishTest() throws InterruptedException {
        applicationEventPublisher.publish("发布消息");
        System.in.read();
    }
}
```

![20210314012257](E:%5CgithubResp%5CSpringBoot-Demo%5CEvent%5Csrc%5Cmain%5Cresources%5Cimg%5C20210314012257.png)

### 异步事件

`Spring` 中的事件默认情况下是同步的，发布者线程会进入阻塞状态，直到所有的监听器处理完事件。如果想让事件监听异步执行，需要在监听器上添加`@Async`, 同时主启动类上添加`@EnableAsync`注解

```java
@Component
public class CustomSpringEventListene {

     @Async
    @EventListener(String.class)
    public void listener(String message) throws InterruptedException {
        System.out.println("1-异步事件监听, 消息为：" + message);
    
}
```

同时支持线程池配置

```java
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("pool")
    public AsyncTaskExecutor  taskExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("consumer-queue-thread-%d").build();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 线程池维护线程的最少数量
        executor.setCorePoolSize(5);
        // 线程池维护线程的最大数量
        executor.setMaxPoolSize(10);
        // 缓存队列
        executor.setQueueCapacity(25);
        //线程名
        executor.setThreadFactory(namedThreadFactory);
        // 线程池初始化
        executor.initialize();
        return executor;
    }
}
```

```java
@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {

    @Async("pool")
    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        System.out.println("接收到的事件：" + event.getMessage());
    }
}
```



### 事务绑定事件

事务绑定事件`@TransactionalEventListener` 是 `@EventListener` 的扩展，`Spring` 允许将事件监听器绑定到事务的某个阶段。

可以绑定到以下事务阶段：

* 如果事务成功完成，则使用AFTER_COMMIT（默认值）来触发事件
* AFTER_ROLLBACK –如果事务已回滚
* AFTER_COMPLETION –如果事务已完成（AFTER_COMMIT和AFTER_ROLLBACK的别名）
* BEFORE_COMMIT用于在事务提交之前立即触发事件

```java
public @interface TransactionalEventListener {

	TransactionPhase phase() default TransactionPhase.AFTER_COMMIT;

    /**
     * 如果没有事务运行是否处理该事件，默认为false
     * 即必须是有事务的时候才会执行
     */
	boolean fallbackExecution() default false;

	@AliasFor(annotation = EventListener.class, attribute = "classes")
	Class<?>[] value() default {};

	@AliasFor(annotation = EventListener.class, attribute = "classes")
	Class<?>[] classes() default {};

	String condition() default "";

}

public enum TransactionPhase {
	BEFORE_COMMIT,
	AFTER_COMMIT,
	AFTER_ROLLBACK,
	AFTER_COMPLETION
}
```

如果A方法的执行需要B方法执行完成的结果，但是A,B不在同一个事务中，B中的事务没有提，A就无法获的数据，此时我们应该等待B提交事务后才执行A方法。

```java
 @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
public void handleCustom(CustomSpringEvent event) {
    System.out.println("事务提交之前");
}

@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
public void handleCustom1(CustomSpringEvent event) {
    System.out.println("事务回滚");
}
```



### 总结

`Spring` 的事件机制可以实现代码解耦，也可以实现异步事件任务。

