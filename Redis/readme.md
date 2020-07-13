## Redis


### 测试性能
#### Redis-benchmark
测试100个并发连接，10000个请求

```java
redis-benchmark -h localhost -p 6379 -c 100 -n 10000
```



###  基本知识

 默认有 **16** 个数据库，默认使用第 **0** 个数据库

```java
[root@iz2ze0obn92kpmakmtjw73z redis]# docker exec -it master-redis redis-cli
127.0.0.1:6379> auth root
OK
127.0.0.1:6379> select 3   // 选择3号数据库
OK
127.0.0.1:6379[3]> dbsize 
(integer) 0
127.0.0.1:6379[3]> flushdb  // 清空当前库
OK
127.0.0.1:6379> FLUSHALL   // 清空所有数据库
OK
127.0.0.1:6379> keys *   // 查看所有键
127.0.0.1:6379> exists key   // 判断key是否存在
127.0.0.1:6379> expire key seconds  // 设置过期时间
127.0.0.1:6379> ttl key    // 查看当前key的剩余时间
127.0.0.1:6379> type key   // 查看当前key的类型
```

> Redis 是单线程的

​	redis是基于内存操作的，CPU不是Redis的性能瓶颈，Redis的性能瓶颈是根据机器的内存和网络带宽，既然可以使用单线程来实现，就使用单线程了。

​	redis是C语言写的，官方提供的数据尾100000+的QPS，

 **redis 为什么单线程还这么快**

1、误区一：高性能的服务器一定是多线程的

2、误区二：多线程（CPU上下文会切换）一定比单线程快

CPU>内存>硬盘的速度

核心：redis是将所有的数据全部放在内存中的，所以说使用单线程去操作效率就是最高的，多线程（CPU上下文会切换：是一个耗时的操作），对于内存系统来说，如果没有上下文切换效率就是最高的。多次读写都是在一个CPU上的，在内存情况下，这个是最佳的方案。



### 五大基本数据类型

#### String

```java
127.0.0.1:6379> strlen name  // 获取key的长度
(integer) 3
127.0.0.1:6379> set key1 'aaa'
OK
127.0.0.1:6379> APPEND key1 'bbb'  // 在key的后面追加
(integer) 6
127.0.0.1:6379> get key1
"aaabbb"
127.0.0.1:6379> set view 1
OK
127.0.0.1:6379> incr view  // 自增1
(integer) 2
127.0.0.1:6379> get view
"2"
127.0.0.1:6379> incr view
(integer) 3
127.0.0.1:6379> get view
"3"
127.0.0.1:6379> incrby view 5  // 指定key加上数值
(integer) 8
127.0.0.1:6379> get view
"8"
127.0.0.1:6379> decr view   // 减一
(integer) 7
127.0.0.1:6379> decrby view 2  // 指定key减去数值
(integer) 5
127.0.0.1:6379> get view
"5"
127.0.0.1:6379> GETRANGE key1 0 3   // 截取字符串，前后都是闭合区间
"aaab"
127.0.0.1:6379> setrange key1 3 c  // 替换指定位置开始的字符串
(integer) 6
127.0.0.1:6379> get key1
"aaacbb"
127.0.0.1:6379> SETEX key2 50 'hello'   // 设置key的过期时间
OK
127.0.0.1:6379> ttl key2
(integer) 48
127.0.0.1:6379> get key2
"hello"
127.0.0.1:6379> setnx key3 'redis'    // 如果key不存在，创建
(integer) 1
127.0.0.1:6379> keys *
1) "key1"
2) "key3"
3) "name"
4) "view"
5) "key2"
127.0.0.1:6379> get keys3
(nil)
127.0.0.1:6379> setnx key3 'MongoDB'   // 如果key存在，创建失败
(integer) 0
127.0.0.1:6379> get key3
"redis"
    
127.0.0.1:6379> mset k1 v1 k2 v2 k3 v3  // 同时设置多个值
OK
127.0.0.1:6379> keys *
1) "k1"
2) "k3"
3) "k2"
127.0.0.1:6379> mget k1 k2 k3    // 同时获取多个值
1) "v1"
2) "v2"
3) "v3"
127.0.0.1:6379> msetnx k1 v1 k4 v4   // msetnx是一个原子性的操作，要么一起成功，要么一起失败
(integer) 0
127.0.0.1:6379> keys *
1) "k1"
2) "k3"
3) "k2"
    
// 对象
127.0.0.1:6379>set user:1 json字符串  // 设置某个用户的信     息 
```

**总结**

String类型的使用场景：value除了是字符串还可以是数字

- 计数器
- 统计多单位的数量
- 粉丝数
- 对象缓存存储



#### List

所有的List命令都是以 **l** 开头的

```shell
127.0.0.1:6379> lpush list one
(integer) 1
127.0.0.1:6379> lpush list two
(integer) 2
127.0.0.1:6379> lpush list three
(integer) 3
127.0.0.1:6379> lrange list 0 -1   // 查看key的数据
1) "three"
2) "two"
3) "one"
127.0.0.1:6379> lrange list 0 1   //通过区间获取具体的值
1) "three"
2) "two"
127.0.0.1:6379> rpush list rightr  // 从右边插入
(integer) 4
127.0.0.1:6379> lrange list 0 -1
1) "three"
2) "two"
3) "one"
4) "rightr"
127.0.0.1:6379> lpop list    // 左边弹出
"three"
127.0.0.1:6379> rpop list    // 右边弹出
"rightr"
    
127.0.0.1:6379> lindex list 0   // 根据索引查询
"two"
127.0.0.1:6379> rindex list 0   // 无此命令
(error) ERR unknown command `rindex`, with args beginning with: `list`, `0`, 
127.0.0.1:6379> llen list   //查询长度
(integer) 2
    
    
127.0.0.1:6379> lpush list five
(integer) 3
127.0.0.1:6379> lrange list 0 -1
1) "five"
2) "two"
3) "one"
127.0.0.1:6379> lpush list five
(integer) 4
127.0.0.1:6379> lpush list five
(integer) 5
127.0.0.1:6379> lrem list 1 five
(integer) 1
127.0.0.1:6379> lrange list 0 -1
1) "five"
2) "five"
3) "two"
4) "one"
127.0.0.1:6379> lrem list 2 five  // 移除指定key指定个数
(integer) 2
127.0.0.1:6379> lrange list 0 -1
1) "two"
2) "one"
127.0.0.1:6379> ltrim list 1 2  // 通过下标截取列表
127.0.0.1:6379> lset list 1 'hello' // 更新指定下标的值，如果下标不存在，会报错

127.0.0.1:6379> lset insert before/after key1 key2 // 将某个具体的value值插入到列的某个元素的前面或者后面
```

**总结**

- 实际上就是一个链表，before node after ,left, right 都可以插入
- 如果key不存在，创建新链表
- 如果key存在，新增内容
- 如果移除了所有值，空链表，也代表不存在
- 在两边插入或者改动值，效率最高，中间元素，相对效率会低
- 可用在消息排队，消息队列，栈