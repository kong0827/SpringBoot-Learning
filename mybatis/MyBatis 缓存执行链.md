## MyBatis 缓存责任链

### 定义

二级缓存是应用级缓存，与一级缓存不同的是它的作用范围是整个应用，可以跨线程使用。二级缓存是存储在`MapperStatement`的成员变量`Cache`中。

### 二级缓存的开启方式

- 方式一：注解方式开发

  `Mapper`接口添加注解 `@CacheNamespace`

  ```java
  @CacheNamespace
  public interface userMapper {
      // 接口方法
  }
  ```

- 方式二：配置文件

  ```xml
  <settings>
      <!-- 开启二级缓存-->
      <setting name="cacheEnabled" value="true"/>
  </settings>
  ```

  同时在`Mapper.xml`文件中配置`cache`，就可以开启二级缓存了。

  ```xml
  <cache />
  ```

这个简单语句的效果如下:

- 映射语句文件中的所有 `select `语句的结果将会被缓存。
- 映射语句文件中的所有 `insert`、`update `和 `delete `语句会刷新缓存。
- 缓存会使用最近最少使用算法（`LRU`, `Least Recently Used`）算法来清除不需要的缓存。
- 缓存不会定时进行刷新（也就是说，没有刷新间隔）。
- 缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。
- 缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改。

**注意**

​	当同时使用`XML`和注解配置二级缓存时。会出现异常。因为`Mapper`接口和对应的`XML`文件使用了共同的命名空间。因此此时可以使用参照缓存的注解解决冲突问题。

```java
@CacheNamespaceRef(UserMapper.class)
public interface UserMapper {
    // 接口方法
}
```

或者

```xml
<cache-ref namespace="com.mybatis.mapper.UserMapper"/>
```

### 责任链

`MyBatis`二级缓存使用了*装饰者模式* 和 *责任链模式*。

 *责任链模式* 是把一个请求传递给多个对象来处理，这些对象都放在一条链上，以实现发送和接受解耦 。各个节点只负责自己的功能。

在`MyBatis` 中的完整的责任链是：

`BlockingCache` ->` SynchronizedCache` ->` LoggingCache` -> `SerializedCache` ->`ScheduledCache` ->`` LruCache` -> PerpetualCache`

![1592156704373](E:\githubResp\SpringBoot-Demo\mybatis\src\main\resources\img\1592156704373.png)

- `BlockingCache`： 使用`ReentrantLock`来防止高速缓存未命中时对数据库的大规模访问，它设置了对高速缓存键的锁定 
- `SynchronizedCache`：同步Cache，实现比较简单，直接使用`synchronized`修饰方法。
- `LoggingCache`：日志功能，装饰类，用于记录缓存的命中率，如果开启了DEBUG模式，则会输出命中率日志。
- `SerializedCache`：序列化功能，将值序列化后存到缓存中。该功能用于缓存返回一份实例的Copy，用于保存线程安全。
- `LruCache`：采用了Lru算法的Cache实现，移除最近最少使用的Key/Value。
- `ScheduledCache`：设置定时刷新缓存。
- `PerpetualCache`： 作为为最基础的缓存类，底层实现比较简单，直接使用了HashMap。

### 源码解析

通过一层层的装饰，形成了一条责任链。 **我们可以通过源码来查看整个责任链的执行过程**。 

`CacheBuilder`类中定义了这个责任链的顺序。通过`CacheBuilder`来创建`Cache`对象，最后将`cache`加入到`configuration` 对象中。

```java
// 创建方法
public Cache build() {
    // 设置默认的缓存容器，过期策略的实现
    setDefaultImplementations(); 
    // 通过反射创建缓存容器对象，并设置id
    Cache cache = newBaseCacheInstance(implementation, id);
    setCacheProperties(cache);
    // issue #352, do not apply decorators to custom caches
    if (PerpetualCache.class.equals(cache.getClass())) {
      for (Class<? extends Cache> decorator : decorators) {
        // Lru 
        cache = newCacheDecoratorInstance(decorator, cache);
        setCacheProperties(cache);
      }
      // 构造执行链
      cache = setStandardDecorators(cache);
    } else if (!LoggingCache.class.isAssignableFrom(cache.getClass())) {
      cache = new LoggingCache(cache);
    }
    return cache;
  }

  // 设置默认的缓存容器，过期策略的实现
  private void setDefaultImplementations() {
    if (implementation == null) {
      implementation = PerpetualCache.class;
      if (decorators.isEmpty()) {
        decorators.add(LruCache.class);
      }
    }
  }

 // 构造执行链
  private Cache setStandardDecorators(Cache cache) {
    try {
      MetaObject metaCache = SystemMetaObject.forObject(cache);
      if (size != null && metaCache.hasSetter("size")) {
        metaCache.setValue("size", size);
      }
      // 默认为null，需要配置ScheduledCache才会被加入到执行链中
      if (clearInterval != null) {
          // 装饰定时刷新功能 
        cache = new ScheduledCache(cache);
        ((ScheduledCache) cache).setClearInterval(clearInterval);
      }
      // 默认为true
      if (readWrite) {
        // 装饰序列功能
        cache = new SerializedCache(cache);
      }
      // 装饰日志功能
      cache = new LoggingCache(cache);
      // 装饰同步功能
      cache = new SynchronizedCache(cache);
      // 默认为false，BlockingCache
      if (blocking) {
          // 装饰防穿透功能
        cache = new BlockingCache(cache);
      }
      return cache;
    } catch (Exception e) {
      throw new CacheException("Error building standard cache decorators.  Cause: " + e, e);
    }
  }
```

**`MapperBuilderAssistant`**

```java
// 创建新的缓存容器
public Cache useNewCache(Class<? extends Cache> typeClass,
      Class<? extends Cache> evictionClass,
      Long flushInterval,
      Integer size,
      boolean readWrite,
      boolean blocking,
      Properties props) {
    Cache cache = new CacheBuilder(currentNamespace)
        // 设置默认的缓存容器，若为null,默认为PerpetualCache，LruCache。可以替换
        .implementation(valueOrDefault(typeClass, PerpetualCache.class))
        .addDecorator(valueOrDefault(evictionClass, LruCache.class))
        // 设置清理时间
        .clearInterval(flushInterval)
        // 设置缓存容量
        .size(size)
        // 同步
        .readWrite(readWrite)
        // 设置防穿透
        .blocking(blocking)
        .properties(props)
        .build();
    // 将缓存容器添加到全局的配置对象范围
    configuration.addCache(cache);
    // 设置当前的缓存
    currentCache = cache;
    return cache;
  }

  private <T> T valueOrDefault(T value, T defaultValue) {
    return value == null ? defaultValue : value; 
  }

 private <T> T valueOrDefault(T value, T defaultValue) {
    return value == null ? defaultValue : value;
  }
```

### 使用配置

​	我们可以对二级缓存执行链进行配置，例如在执行链中默认`BlockingCache`和`ScheduledCache`是默认关闭的，我们可以通过配置进行修改，同时也修改默认的缓存容器。

​	下面通过注解开发的方式，配置`XML`方式原理相同。

​	使用二级缓存首先在相应的`Mapper`接口上添加`@CacheNameSpace`注解。` @CacheNamespace`注解主要用于`mybatis`二级缓存，等同于`<cache />`属性。 我们可以先查看其源码，进一步的了解。

```java
public @interface CacheNamespace {
    // 缓存实现,默认PerpetualCache
  Class<? extends org.apache.ibatis.cache.Cache> implementation() default 		PerpetualCache.class;

    // 过期策略，默认LruCache
  Class<? extends org.apache.ibatis.cache.Cache> eviction() default LruCache.class;

    // 刷新缓存的时间，默认为0，执行下一条语句的时候进行刷新
  long flushInterval() default 0;

    // 缓存容量
  int size() default 1024;

    // 序列化，默认使用对应的Cache
  boolean readWrite() default true;

    // 防穿透，默认不使用
  boolean blocking() default false;

	// 缓存组件对应的属性    
  Property[] properties() default {};
}`
```



- **修改默认的实现方式**

  ```java
  // 需要实现Cache接口
  public class DiskCache implements Cache {
     private final String id;
      private String cachePath;
  
      public DiskCache(String id) {
          this.id = id;
      }
      // ···
      // 省略实现方式
  }
  ```

  `Mapper`接口，修改默认的·实现方式

  ```java
  @CacheNamespace(implementation = DiskCache.class, properties = {@Property(name = "cachePath",
          value ="E:\\githubResp\\SpringBoot-Demo\\mybatis\\src\\main\\resources" )})
  public interface UserMapper {
  }
  ```

  测试

  ```java
  @Test
  public void test() {
      Cache cache = configuration.getCache("UserMapper");
      cache.putObject("cache store", "hahaha....");
  }
  ```

- **修改缓存的溢出淘汰策略**

  `Mapper`接口

  ```java
   @CacheNamespace(eviction = FifoCache.class, size = 10)
  ```

  测试

  ```java
   Cache cache = configuration.getCache("UserMapper");
  for (int i = 0; i < 12; i++) {
      cache.putObject("kxj:" + i, i);
  }
  ```

  溢出淘汰策略，`MyBatis`提供了以下几种淘汰算法，默认是`Lru`

  - LRU：最近最少使用
  - FIFO：先进先出
  - SOFT：软引用，基于垃圾回收器状态和软引用规则来移除对象
  - WEAK：弱引用，基于垃圾回收器状态和弱引用规则来对象

- **修改序列化**

  序列化默认是开启的，我们可以通过配置将序列化关闭

  ```java
  @CacheNamespace(readWrite=false)
  ```

  测试

  ```java
   @Test
      public void test4() {
          Cache cache = configuration.getCache("UserMapper");
          cache.putObject("user", Mock.newUser());
          Object user = cache.getObject("user");
          Object user1 = cache.getObject("user");
          System.out.println(user == user1);  // 如果走序列化，为false，如果关闭序列化，则为true
      }
  ```

- **过期清理时间**

  ```java
   @CacheNamespace(flushInterval = 10000)
  ```

  测试

  ```java
  @Test
  public void test5() throws InterruptedException {
      Cache cache = configuration.getCache("UserMapper");
      cache.putObject("user", "hello");
      System.out.println(cache.getObject("user")); // 缓存中有数据
      Thread.sleep(11000);   // 当时间超过设置的刷新缓存的时间，缓存会被清空
      System.out.println(cache.getObject("user")); // 查询不到值，缓存中已无数据
  }
  ```

  

### 总结

1.  `MyBatis`二级缓存使用了*装饰者模式* 和 *责任链模式*
2. 责任链模式中顺序不可修改，已经定义好执行链的顺序。但是可以修改默认的实现方式，缓存清理策略，开闭序列化，防穿透等缓存组件。

​	



