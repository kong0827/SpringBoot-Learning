

## MyBatis源码解析 二级缓存

### 简介

​	二级缓存是应用级缓存，与一级缓存不同的是它的作用范围是整个应用，可以跨线程使用。二级缓存是基于` Configuration` 的缓存，存储在`MapperStatement`的成员变量`Cache`中。

​	二级缓存的原理和一级缓存的原理相同，第一个查询会将数据放入到缓存中，然后第二个查询直接查缓存，多个`SqlSession`可以在映射器中共享二级缓存。如果两个映射器的名称空间相同，那么这两个映射器共用一个缓存空间。

### 使用

 二级缓存是默认关闭的，具体开启用法参照《MyBatis源码解析三 缓存执行链》。 https://juejin.im/post/5ee6675af265da76e029bd72 

### 缓存命中场景

缓存命中场景大致同一直缓存，要求`CacheKey`相同，即`MappedStatementId`， `sql`， sql的偏移量，sql的限制以及参数。可参考《MyBatis源码解析二 一级缓存》 https://juejin.im/post/5edfc7bf6fb9a047f129d288 

![1592412895162](E:\githubResp\SpringBoot-Demo\mybatis\src\main\resources\img\1592412895162.png)



#### 为什么提交后才能命中缓存

![image-20210414002708968](E:%5CgithubResp%5CSpringBoot-Demo%5Cmybatis%5Csrc%5Cmain%5Cresources%5Cimg%5Cimage-20210414002708968.png)

如果不是提交后才能命中，会造成脏读的问题

![image-20210414003816582](C:%5CUsers%5C%E5%B0%8FK%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210414003816582.png)

每个会话会有单独的事务缓存管理器，事务的缓存管理器会有多个暂存区（查询的数据，例如一个会话可以查多个表数据），每个暂存器都会指向唯一一个缓存区。

commit后会将暂存区的数据放到缓存区

### 执行流程

![1592238509238](E:\githubResp\SpringBoot-Demo\mybatis\src\main\resources\img\1592238509238.png)

```java
@Test
public void cacheTest() {
    SqlSession sqlSession = factory.openSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    User user = mapper.selectById(1);
}
```

以查询为例，去深入了解`MyBatis` 二级缓存的执行流程。

- 首先创建 `SqlSession` 对象

  `Configuration`

  默认创建的执行器为`SimpleExecutor`

  ```java
  // 创建执行器
  public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
      executorType = executorType == null ? defaultExecutorType : executorType;
      executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
      Executor executor;
      // 判断执行器类型
      if (ExecutorType.BATCH == executorType) {
        executor = new BatchExecutor(this, transaction);
      } else if (ExecutorType.REUSE == executorType) {
        executor = new ReuseExecutor(this, transaction);
      } else {
        executor = new SimpleExecutor(this, transaction);
      }
      // 判断是否开启二级缓存，开启后对上面的执行器再进行一次包装
      if (cacheEnabled) {
        executor = new CachingExecutor(executor);
      }
      executor = (Executor) interceptorChain.pluginAll(executor);
      return executor;
    }
  ```

- 获取 ` mapper  `对象

- 执行查询操作

  调用`SqlSession` 接口的 `SelectList`方法，然后调用器实现类`DefaultSqlSession`的`selectList`方法

  `DefaultSqlSession`

  `SqlSession` 将特定的查询职责交给了 `Executor`

  ![1592237216844](E:\githubResp\SpringBoot-Demo\mybatis\src\main\resources\img\1592237216844.png)

  `CachingExecutor`
  
  `MyBatis`中的二级缓存是通过`CachingExecutor`实现的。如果仅开启一级缓存，走`BaseExecutor`的查询方法。若开启了二级缓存，先走`CachingExecutor`的查询方法
  
  ```java
  @Override
  public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
      // 获取BoundSql对象，包含sql,参数等
      BoundSql boundSql = ms.getBoundSql(parameterObject);
      // 创建缓存的key，以后根据这个key获取缓存
      CacheKey key = createCacheKey(ms, parameterObject, rowBounds, boundSql);
      return query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
  }
  
  @Override
  public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql)
      throws SQLException {
      // 从MappedStatement获取缓存
      Cache cache = ms.getCache();
      if (cache != null) {
          // 根据flushCache判断是否需要清除缓存
          flushCacheIfRequired(ms);
          if (ms.isUseCache() && resultHandler == null) {
              // 处理存储过程
              ensureNoOutParams(ms, boundSql);
              // 从缓存中获取数据，实际上是走责任链，获取数据
              List<E> list = (List<E>) tcm.getObject(cache, key);
              if (list == null) {
                  // 缓存数据为空，从数据库中获取，并填充到缓存中.delegate是SynchronizedCache
                  list = delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
                  // tcm 事务缓存管理器
                  tcm.putObject(cache, key, list);
              }
              return list;
          }
      }
      // 没有开启二级缓存，执行BaseExecutor，走一级缓存。delegate默认是SimpleExecutor
      return delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
  }
  ```
  
  调用`TransactionalCache` 将结果放入到 `entriesToAddOnCommit` 的`map`集合中
  
  ```java
  public void putObject(Cache cache, CacheKey key, Object value) {
      getTransactionalCache(cache).putObject(key, value);
  }
  public void putObject(Object key, Object object) {
      entriesToAddOnCommit.put(key, object);
  }
  ```
  
  
  
  `TransactionalCacheManager` 
  
  `TransactionalCacheManager`  中 保存着缓存和事务缓存的映射关系。`TransactionalCache` 也实现了 `Cache` 接口。每个会话都有对应的事务缓存管理器，在缓存管理器中放着不同查询的缓存结果，当执行提交时或者`SqlSession`关闭时会将查询结果刷到缓存中
  
  ```java
  private final Map<Cache, TransactionalCache> transactionalCaches = new HashMap<>();
  ```
  
  ![1592412664486](E:\githubResp\SpringBoot-Demo\mybatis\src\main\resources\img\1592412664486.png)
  
  
  
  `TransactionalCacheManager`  用来管理 `Cache`，每个`Cache` 都会使用 `TransactionalCacheManager` 进行装饰，必须手动提交才能将 结果放到缓存中，这个是事务性的。
  
  ![1592413005592](E:\githubResp\SpringBoot-Demo\mybatis\src\main\resources\img\1592413005592.png)
  
  在`CachingExecutor`中执行提交方法，才会调用`TransactionalCacheManager` 的提交方法，最终到了` TrancationalCache ` 中的提交方法。
  
  ```java
  // CachingExecutor
  public void commit(boolean required) throws SQLException {
      delegate.commit(required);
      tcm.commit();
    }
  
  // TrancationalCache 
   public void commit() {
       if (clearOnCommit) {
           delegate.clear();
       }
       flushPendingEntries();
       reset();
   }
  
  // entriesToAddOnCommit遍历放入缓存中
  private void flushPendingEntries() {
      for (Map.Entry<Object, Object> entry : entriesToAddOnCommit.entrySet()) {
        delegate.putObject(entry.getKey(), entry.getValue());
      }
      for (Object entry : entriesMissedInCache) {
        if (!entriesToAddOnCommit.containsKey(entry)) {
          delegate.putObject(entry, null);
        }
      }
    }
  ```
  
  
  
  `CacheKey`
  
  当我们根据`CacheKey`从缓存中取出数据，是如何判断`CacheKey`是相等的呢。在`CacheKey` 中定义了一个`equals`方法。
  
  ```java
   public boolean equals(Object object) {
      if (this == object) {
        return true;
      }
      if (!(object instanceof CacheKey)) {
        return false;
      }
  
      final CacheKey cacheKey = (CacheKey) object;
  
      if (hashcode != cacheKey.hashcode) {
        return false;
      }
      if (checksum != cacheKey.checksum) {
        return false;
      }
      if (count != cacheKey.count) {
        return false;
      }
  
       // 遍历，只要updatelist中的元素相同，则CacheKey相同
      for (int i = 0; i < updateList.size(); i++) {
        Object thisObject = updateList.get(i);
        Object thatObject = cacheKey.updateList.get(i);
        if (!ArrayUtil.equals(thisObject, thatObject)) {
          return false;
        }
      }
      return true;
    }
  ```
  
  

### 总结

- `MyBatis` 的二级缓存实现了`SqlSession` 之间的缓存数据共享

- `MyBatis` 的二级缓存查询时必须提交，才能真正放入到缓存中。插入，修改，删除操作会清空缓存

- `MyBatis` 的二级缓存是通过`CachingExecutor`实现的

  