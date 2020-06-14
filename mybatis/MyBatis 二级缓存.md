## MyBatis 二级缓存责任链的建立

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
  ` `
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

`MapperBuilderAssistant`

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
        .clearInterval(flushInterval)
        .size(size)
        .readWrite(readWrite)
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



### 使用

未完待续、、、