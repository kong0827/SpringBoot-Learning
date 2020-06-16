## MyBatis一级缓存

#### 简介

​	`MyBatis`提供了一级缓存的方案来优化数据库会话之间重复查询的问题，每个`SqlSession`都有自己的缓存，不同的会话之间的缓存互不影响。在`MyBatis`框架中一级缓存是通过`HashMap`实现的，默认作用范围是`SqlSession`。

#### 缓存命中场景

​	当执行条件完全相同的`sql` 时，就会命中`MyBatis`的一级缓存。那么条件完全相同是指那些条件呢？

- 同一个会话
- 参数相同，`sql`相同
- `Statement`相同
- 未手动清空缓存（提交，回滚，`update`操作），
- 缓存的作用域不是`STATEMENT`

##### 一级缓存实验

1. 当查询条件完全一样时，命中缓存

   ```java
   UserMapper mapper = sqlSession.getMapper(UserMapper.class);
   User user = mapper.selectById(10);
   User user1 = mapper.selectById(10);
   System.out.println(user == user1);  // true
   ```

2. 当会话不同时，无法命中缓存

   ```java
   // 会话不相同
   UserMapper userMapper = sqlSessionFactory.openSession().getMapper(UserMapper.class);
   User user2 = userMapper.selectById(10);
   System.out.println(user == user2); // false
   ```

3. 当参数不同时， 无法命中缓存

   ```java
   User user3 = mapper.selectById(11);
   System.out.println(user == user3); // false
   ```

4. `statementId`不同时，无法命中缓存

   ```java
   // statementId不同
   // com.kxj.mybatis.UserMapper.selectById
   // com.kxj.mybatis.UserMapper.selectById3
   User user4 = mapper.selectById3(11);
   System.out.println(user == user4); // false
   ```

5. RowBounds不同时，无法命中缓存

   ```java
    // RowBounds不同 默认 RowBounds.DEFAULT
   RowBounds rowBounds = new RowBounds(0, 10);
   List<Object> list = sqlSession.selectList("com.kxj.mybatis.UserMapper.selectById",
                                             10, rowBounds);
   System.out.println(list.get(0) == user); // false
   
   List<Object> list1 = sqlSession.selectList("com.kxj.mybatis.UserMapper.selectById",
                                              10, RowBounds.DEFAULT);
   System.out.println(list1.get(0) == user); // true
   ```

6. 手动清空缓存(提交或者回滚)，无法命中缓存

   ```JAVA
   UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
   User user = userMapper.selectById(10);
   
   // 提交
   sqlSession.commit();
   // 或者 sqlSession.rollback();
   
   User user1 = userMapper.selectById(10);
   System.out.println(user == user1);  // false
   ```

7. 配置`flushCache = true`，无法命中缓存

   ```java
   / * 
     *  @Select({"select * from users where id=#{1}"})
     *  @Options(flushCache = Options.FlushCachePolicy.TRUE)
     *  User selectById3(Integer id);
     * /
   User user2 = userMapper.selectById3(10);
   User user3 = userMapper.selectById3(10);
   System.out.println(user2 == user3);  // false
   ```

8. 查询中执行`update`（实质上会清空缓存），无法命中缓存

   ```java
   // 执行update操作
   User user4 = userMapper.selectById(10);
   userMapper.setName(10, "kxj");
   User user5 = userMapper.selectById(10);
   System.out.println(user4 == user5);  // false
   ```

9. 缓存的作用域是`STATEMENT`，无法命中缓存

   ```java
    // 缓存的作用域是STATEMENT
   // <setting name="localCacheScope" value="STATEMENT"/>
   User user6 = userMapper.selectById(10);
   User user7 = userMapper.selectById(10);
   System.out.println(user6 == user7);  // false
   ```

##### 总结命中场景

![1591720580930](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591720580930.png)



#### 执行过程

​	每个`SqlSession`会话中会创建`Executor`执行器，在`BaseExcutor`执行器中维护了`localCache`。

- 用户发起查询请求，创建会话
- 调用`Executor`的 `query`方法
- 调用`BaseExecutor` 的 `query`方法
- 判断`BaseExecutor` 中缓存(`localCache`)是否存在
- 存在从缓存中取
- 不存在调用子类的`doQuery`方法，从数据库查询，并设置进缓存

![1591720656046](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591720656046.png)

#### 源代码分析

​	一级缓存主要需要研究` BaseExecutor `类，` BaseExecutor `是实现了` Executor`接口的抽象类，主要功能有改、查、缓存维护、事务管理以及提交，批处理刷新等。

- `BaseExecutor`维护了`localCache`这个成员变量

  ![1591721811550](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591721811550.png)

- `localCache`的作用范围（`SESSION,STATEMENT`），默认`SESSION`

  ![1591723708542](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591723708542.png)

- 缓存的类型是`PrepetualCache`，查看源代码，内部实际上维护了一个`HashMap`，一级缓存的操作实际上是对这个`map`的操作。

  ![1591721891146](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591721891146.png)

- `Executor`会调用`BaseExecutor`的`query`方法，同时会生成`CacheKey`

  ![1591722361541](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591722361541.png)

- 这个`CacheKey`相同时，就会命中缓存，查看源代码查看到`CacheKey`的属性。它和 MappedStatement的ID，分页信息，Sql本身和Sql中的参数，环境相关。 

  ![1591723080540](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591723080540.png)

- 查看`query`方法

  ![1591723329168](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591723329168.png)

- 当执行提交，回滚，更新等操作时会清空缓存

  ![1591723522457](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591723522457.png)

  ![1591723408137](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591723408137.png)

#### 总结

1. `MyBatis`一级缓存是会话`SqlSession`级别的缓存，
2. `MyBatis`一级缓存底层使用`HashMap`进行储存，是线程不安全的
3. `MyBatis`一级缓存范围有`SESSION,STATEMENT`

