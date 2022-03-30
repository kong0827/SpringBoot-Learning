### @Async自定义线程池及异常处理

　　在项目中，当访问其他人的接口较慢或者做耗时任务时，不想程序一直卡在耗时任务上，想程序能够并行执行，我们可以使用多线程来并行的处理任务，也可以使用spring提供的异步处理方式@Async。

`　　Spring`是通过任务执行器(`TaskExecutor`)来实现多线程和并发编程，使用 `ThreadPoolTaskExecutor`来创建一个基于线程池的 `TaskExecutor`。在使用线程池的大多数情况下都是异步非阻塞的。我们配置注解 `@EnableAsync`可以开启异步任务，然后在实际执行的方法上配置注解 `@Async`上声明是异步任务。通过 `@Async`注解表明该方法是异步方法，如果注解在类上，那表明这个类里面的所有方法都是异步的。

#### 一、如何使用

1、开启异步支持 —— 使用 @EnableAsync 启用异步注解

```java
@Configuration
@EnableAsync
public class SpringAsyncConfig { ... }
```

2、异步处理方式 —— @Async注解使用

（1）无返回值 —— 调用之后，不返回任何数据。无返回值的话，和常规写法没什么不同。

```java
@Async
public void asyncMethodWithVoidReturnType() {
　　System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
}
```

（2）有返回值 —— 调用之后，返回数据，通过Future来获取返回数据。有返回值的话，需要将返回值包在 Future 对象中。Future 对象是专门存放异步响应的一个接口。

　　异步调用返回数据，Future表示在未来某个点获取执行结果，返回数据类型可以自定义

```java
    @Async
    public Future<String> dealHaveReturnTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("thread", Thread.currentThread().getName());
        jsonObject.put("time", System.currentTimeMillis());
        return new AsyncResult<String>(jsonObject.toJSONString());
    }
```



　　测试类用 isCancelled 判断异步任务是否取消，isDone 判断任务是否执行结束

```java
    @Test
    public void testDealHaveReturnTask() throws Exception {
        Future<String> future = asyncTask.dealHaveReturnTask();
        log.info("begin to deal other Task!");
        while (true) {
            if(future.isCancelled()){
                log.info("deal async task is Cancelled");
                break;
            }
            if (future.isDone() ) {
                log.info("deal async task is Done");
                log.info("return result is " + future.get());
                break;
            }
            log.info("wait async task to end ...");
            Thread.sleep(1000);
        }
    }
```



日志打印如下，我们可以看出任务一直在等待异步任务执行完毕，用 future.get() 来获取异步任务的返回结果。

```java
begin to deal other Task!
wait async task to end ...
wait async task to end ...
wait async task to end ...
wait async task to end ...
deal async task is Done
return result is {"thread":"AsyncExecutorThread-1","time":1499752617330}
```



#### 二、定义线程池

1、第一步，先在Spring Boot主类中定义一个线程池，比如

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @EnableAsync
    @Configuration
    class TaskPoolConfig {
        @Bean("taskExecutor")
        public Executor taskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(10);
            executor.setMaxPoolSize(20);
            executor.setQueueCapacity(200);
            executor.setKeepAliveSeconds(60);
            executor.setThreadNamePrefix("taskExecutor-");
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            return executor;
        }
    }

}
```



　　上面我们通过使用 `ThreadPoolTaskExecutor`创建了一个线程池，同时设置了以下这些参数：

- 核心线程数10：线程池创建时候初始化的线程数
- 最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
- 缓冲队列200：用来缓冲执行任务的队列
- 允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
- 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
- 线程池对拒绝任务的处理策略：这里采用了`CallerRunsPolicy`策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务

2、如何使用线程池

　　在定义了线程池之后，我们如何让异步调用的执行任务使用这个线程池中的资源来运行呢？方法非常简单，我们只需要在`@Async`注解中指定线程池名即可，比如

```java
    @Async("taskExecutor")
    public void doTaskOne() throws Exception {
        log.info("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务一，耗时：" + (end - start) + "毫秒");
    }
```



#### 三、异常处理

　　默认的，打开异步开关后，Spring 会使用一个 `SimpleAsyncTaskExecutor` 作为线程池，该线程默认的并发数是不受限制的。所以每次异步方法来，都会获取一个新线程去运行它。

1、`AsyncConfigurer` 接口

　　Spring 4 中，对异步方法可以做一些配置，将配置类实现` AsyncConfigurer` 接口后，可以实现自定义线程池的功能，和统一处理异步方法的异常。

（1）如果不限制并发数，可能会造成系统压力。`AsyncConfigurer` 接口中的方法 `Executor getAsyncExecutor() `实现自定义线程池，控制并发数。

（2）`AsyncConfigurer` 接口中的方法 `public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler()` 用于处理异步方法的异常。

　　`AsyncUncaughtExceptionHandler` 接口，只有一个方法：`void handleUncaughtException(Throwable ex, Method method, Object… params);`

　　因此，`AsyncUncaughtExceptionHandler` 接口可以认为是一个函数式接口，可以用拉姆达表达式实现该接口。

2、代码示例

（1）我们可以实现`AsyncConfigurer`接口，也可以继承`AsyncConfigurerSupport`类来实现，在方法`getAsyncExecutor()`中创建线程池的时候，必须使用 `executor.initialize()`，不然在调用时会报线程池未初始化的异常。

　　如果使用`threadPoolTaskExecutor()`来定义`bean`，则不需要初始化。

```java
@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

//    @Bean
//    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(10);
//        executor.setMaxPoolSize(100);
//        executor.setQueueCapacity(100);
//        return executor;
//    }
　　// 自定义线程池，控制并发数，将线程池的大小设置成只有10个线程
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncExecutorThread-");
        executor.initialize(); //如果不初始化，导致找到不到执行器
        return executor;
    }　　// 统一处理异常
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
}
```



（2）异步异常处理类：

```java
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.info("Async method: {} has uncaught exception,params:{}", method.getName(), JSON.toJSONString(params));

        if (ex instanceof AsyncException) {
            AsyncException asyncException = (AsyncException) ex;
            log.info("asyncException:{}",asyncException.getErrorMessage());
        }

        log.info("Exception :");
        ex.printStackTrace();
    }
}

@Data
@AllArgsConstructor
public class AsyncException extends Exception {
    private int code;
    private String errorMessage;
}
```



　　2.1、在无返回值的异步调用中，异步处理抛出异常，`AsyncExceptionHandler`的`handleUncaughtException()`会捕获指定异常，原有任务还会继续运行，直到结束。

　　2.2、在有返回值的异步调用中，异步处理抛出异常，会直接抛出异常，异步任务结束，原有处理结束执行





#### 总结

实现`AsyncConfigurer`接口对异常线程池更加细粒度的控制
a) 创建线程自己的线程池
b) 对void方法抛出的异常处理的类`AsyncUncaughtExceptionHandler`

```java
/**
 * 通过实现AsyncConfigurer自定义异常线程池，包含异常处理
 * 
 * @author hry
 *
 */
@Service
public class MyAsyncConfigurer implements AsyncConfigurer{
    private static final Logger log = LoggerFactory.getLogger(MyAsyncConfigurer.class);

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();  
        threadPool.setCorePoolSize(1);  
        threadPool.setMaxPoolSize(1);  
        threadPool.setWaitForTasksToCompleteOnShutdown(true);  
        threadPool.setAwaitTerminationSeconds(60 * 15);  
        threadPool.setThreadNamePrefix("MyAsync-");
        threadPool.initialize();
        return threadPool;  
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
         return new MyAsyncExceptionHandler();  
    }

    /**
     * 自定义异常处理类
     * @author hry
     *
     */
    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {  

        @Override  
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {  
            log.info("Exception message - " + throwable.getMessage());  
            log.info("Method name - " + method.getName());  
            for (Object param : obj) {  
                log.info("Parameter value - " + param);  
            }  
        }  

    } 

}
@SpringBootApplication
@EnableAsync // 启动异步调用
public class AsyncApplicationWithAsyncConfigurer {
    private static final Logger log = LoggerFactory.getLogger(AsyncApplicationWithAsyncConfigurer.class);

    public static void main(String[] args) {
        log.info("Start AsyncApplication.. ");
        SpringApplication.run(AsyncApplicationWithAsyncConfigurer.class, args);
    }


}
```

#### 源码解析

```java
public interface AsyncConfigurer {

	/**
	 * The {@link Executor} instance to be used when processing async
	 * method invocations.
	 */
	@Nullable
	default Executor getAsyncExecutor() {
		return null;
	}

	/**
	 * The {@link AsyncUncaughtExceptionHandler} instance to be used
	 * when an exception is thrown during an asynchronous method execution
	 * with {@code void} return type.
	 */
	@Nullable
	default AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}

}

```

这个接口作用只有两个

1. 配置异步线程池；
2. 配置AsyncUncaughtExceptionHandler。

第一个作用在此就不做解释了，第二个作用也简单，如果你的方法没有返回类型又想处理未捕获异常异常的话，用它就是了。
源码如下：

```java
@FunctionalInterface
public interface AsyncUncaughtExceptionHandler {

	/**
	 * Handle the given uncaught exception thrown from an asynchronous method.
	 * @param ex the exception thrown from the asynchronous method
	 * @param method the asynchronous method
	 * @param params the parameters used to invoked the method
	 */
	void handleUncaughtException(Throwable ex, Method method, Object... params);

}

```





#### 1. TaskExecutor

Spring异步线程池的接口类，其实质是`java.util.concurrent.Executor`

Spring 已经实现的异常线程池：

1. `SimpleAsyncTaskExecutor`：不是真的线程池，这个类不重用线程，每次调用都会创建一个新的线程。（我们使用了这个线程池，是默认的）
2. `SyncTaskExecutor`：这个类没有实现异步调用，只是一个同步操作。只适用于不需要多线程的地方
3. `ConcurrentTaskExecutor`：Executor的适配类，不推荐使用。如果`ThreadPoolTaskExecutor`不满足要求时，才用考虑使用这个类
4. `SimpleThreadPoolTaskExecutor`：是`Quartz`的`SimpleThreadPool`的类。线程池同时被quartz和非quartz使用，才需要使用此类
5. `ThreadPoolTaskExecutor` ：最常使用，推荐。 其实质是对`java.util.concurrent.ThreadPoolExecutor`的包装



#### 事务问题

A方法调用B方法
A方法有事务，B方法有@async异步
B方法会新起事务，B方法的执行成功与否不影响A事务的执行



