MyBatis 源码解析

### 执行器 Executor

#### JDBC执行过程

- MyBatis的底层是JDBC实现的，首选回顾JDBC的执行过程

  ![1591546157028](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591546157028.png)

  注：新版本的JDBC提供了SPI，不需要显示的注册驱动，因此注册驱动这一步可以省略

- 代码示例

  ```java
  /** 注册数据库驱动 - 省略 **/
  // Class.forName("com.mysql.jdbc.Driver"); 
  /** 第一步： 获取连接 */
  Connection connection = DriverManager
                  .getConnection(URL, USERNAME, PASSWORD);
  /** 第二步： 预编译SQL */
  PreparedStatement statement = connection
                  .prepareStatement("select * from  user");
  /** 第三步： 执行查询 */
  ResultSet resultSet = statement.executeQuery();
  /** 第四步： 读取结果 */
  readResultSet(resultSet);
  ```

##### Statement- JDBC 执行器

1. **JDBC有三种执行器：Statement, PreparedStatement, CallableStatement**

   -  `Statement`、`PreparedStatement`和`CallableStatement`都是接口 

   - `Statement`继承自`Wrapper`、`PreparedStatement`继承自`Statement`、`CallableStatement`继承自`PreparedStatement `

     ![1591547020025](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591547020025.png)

   - 区别

     1、**Statement**

     ​		普通的不带参数的查询SQL，无法防止SQL注入

     2、**PreparedStatement**

     ​		可进行预编译，编译一次，执行多次，效率高，可防止SQL注入

     3、**CallableStatement**

     ​		支持带参数的SQL操作，支持调用存储过程

     ![1591547199951](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591547199951.png)

     

   - 方法

     1. addBatch: 批处理操作，将多个SQL合并在一起，最后调用executeBatch 一起发送至数据库执行

     2. setFetchSize:设置从数据库每次读取的数量单位。该举措是为了防止一次性从数据库加载数据过多，导致内存溢出。

        ![1591547560992](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591547560992.png)

### MyBatis执行过程

#### 执行过程概览

MyBatis的执行流程如下图（省略了加载配件文件，创建会话工厂等步骤）

![1591548229827](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591548229827.png)

上图可用以下代码来表示

```java
// 获取构建器
SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
// 解析XML 并构造会话工厂
factory = factoryBuilder.build(ExecutorTest.class.getResourceAsStream("/mybatis-com.kxj.config.xml"));
// 常见SqlSession
SqlSession sqlSession = factory.openSession(true);
// 操作Mapper接口
UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
// 获取结果
User user = userMapper.getUser(1)
```

#### SqlSession 会话

#### Executor 执行器

- 概念

  执行器用于连接 SqlSession与JDBC，所有与JDBC相关的操作都要通过它。图中展示了Executor在核心对象中所处位置。 

   ![image-20200226173524028](http://coderead.cn/p/mybatis/html/img/image-20200226173524028.png) 

- 功能

  基本功能：修改，查询，缓存维护，事务管理。将多个的SQL的共性抽取出来，放在执行器中

  1. 没有添加和删除的方法，默认此这来两个方法走的是修改操作
  2. 这里的缓存维护指一级缓存
  3. 事务管理，提交，回滚，关闭，批处理刷新

  ![1591549047833](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591549047833.png)

#### 执行体系

![1591633589007](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591633589007.png)

- 简单执行器 - `SimpleExecutor`

  简单执行器是默认的执行器。一个`Statement`只执行一次，执行完毕后则进行销毁。

  ![1591631749927](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591631749927.png)

  ![1591631860561](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591631860561.png)

  ![1591632183993](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591632183993.png)

- 可重用执行器 - `ReuseExecutor`

  可重用主要指的是`Statement`可以重复使用。利用Map将Statement进行缓存，每次执行前判断是否存在此`Statement`。

  ![1591632303174](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591632303174.png)

  ![1591632497089](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591632497089.png)

  - `SqlSession`和`ReuseExecutor`

  ![1591626708493](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591626708493.png)

  ​	

- 批处理执行求 - `BatchExecutor`

  将多个`Statement`对应的SQL语句一次传输（调用`flushStatement`方法）到数据库中，进行批处理操作。

  ![1591632779248](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591632779248.png)

  ![1591632722229](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591632722229.png)

  ![1591633004614](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591633004614.png)

  `SqlSession`和`BatchExecutor`

  ![1591626796262](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591626796262.png)

- 基础执行器 - `BaseExecutor`

  主要进行缓存维护，事务管理

- 缓存（二级缓存）执行器 - `CachingExecutor`

  利用装饰器模式对`Executor`进行包装，开启缓存后，默认先从缓存中获取数据，获取不到再从数据库中取数据。

  ![1591633118209](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591633118209.png)

  ![1591633399295](https://github.com/kong0827/SpringBoot-Demo/blob/master/mybatis/src/main/resources/img/1591633399295.png)



### 总结

 Executor是Mybatis的一个核心接口，每一个SqlSession对象都会拥有一个Executor(执行器对象) 

