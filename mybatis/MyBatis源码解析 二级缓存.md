## MyBatis源码解析 二级缓存

### 简介

​	二级缓存是应用级缓存，与一级缓存不同的是它的作用范围是整个应用，可以跨线程使用。二级缓存是基于` Configuration` 的缓存，存储在`MapperStatement`的成员变量`Cache`中。

​	二级缓存的原理和一级缓存的原理相同，第一个查询会将数据放入到缓存中，然后第二个查询直接查缓存，多个`SqlSession`可以在映射器中共享二级缓存。如果两个映射器的名称空间相同，那么这两个映射器共用一个缓存空间

### 缓存命中场景

![1592235696805](E:\githubResp\SpringBoot-Demo\mybatis\src\main\resources\img\1592235696805.png)



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

以上述代码为例，去深入了解`MyBatis` 二级缓存的执行流程。

- 首先创建 `SqlSession` 对象

- 获取 ` mapper  `对象

- 执行查询操作

  调用`SqlSession` 接口的 `SelectList`方法，然后调用器实现类`DefaultSqlSession`的`selectList`方法

  `DefaultSqlSession`

  ![1592237216844](E:\githubResp\SpringBoot-Demo\mybatis\src\main\resources\img\1592237216844.png)

  `CachingExecutor`

  ```java
  @Override
  public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
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
              ensureNoOutParams(ms, boundSql);
              // 从缓存中获取数据，实际上是走责任链，获取数据
              List<E> list = (List<E>) tcm.getObject(cache, key);
              if (list == null) {
                  // 缓存数据为空，从数据库中获取，并填充到缓存中
                  list = delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
                  tcm.putObject(cache, key, list); // issue #578 and #116
              }
              return list;
          }
      }
      // 调用 BaseExecutor的query方法查询，二级缓存先于一级缓存执行
      return delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
  }
  ```

### 实例化过程

