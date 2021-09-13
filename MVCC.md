## MySQL事务和MVCC机制 

![image-20210822235520360](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210822235520360.png)

![image-20210822235823300](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210822235823300.png)

![image-20210823001108846](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823001108846.png)

![image-20210823000552676](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823000552676.png)



![image-20210823001737139](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823001737139.png)









**什么是MVCC？**

多版本并发控制(Multi-Version Concurrency Control, MVCC)

仅在读提交和可重复读两种隔离级别下生效

每行记录字段都保存有 一个最近变更事务Id 一个最新删除的事务Id

事务读数据的原则就是： 读版本号小于等于当前版本的数据(意思就是读不到在当前事务之后修改的数据 避免了不可重复读)

　　　　　　　　　　　 读删除事务版本号大于等于当前版本的数据(意思就是如果这条数据在之后的事务里删了，当前事务也不能再读了) 

InnoDB实现mvcc 是通过 readview+undolog 来实现



**什么是幻读？**

不可重复读侧重于update这种操作，同一条数据前后读起来不一样的情况,

幻读侧重于insert delete这种操作，前后两次select 数据的数量会发生变化



rr是不可以解决幻读的，他解决的只是查询中的幻读现象，而对于修改操作是不能解决的



![image-20210823234857308](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823234857308.png)

![image-20210823235015518](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823235015518.png)





![image-20210823235407502](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823235407502.png)

![image-20210823235421061](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823235421061.png)

![image-20210823235434137](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823235434137.png)

### 事务并发场景

- 读-读：两个事务都对该数据进行读操作，不会影响数据的状态，不会带来数据不一致的情况
- 读-写：有线程安全问题，会存在数据不一致问题
- 写-写:  有线程安全问题，

### 事务的隔离级别

| 隔离级别 | 脏读   | 不可重复读 | 幻读   |
| -------- | ------ | ---------- | ------ |
| 读未提交 | 可能   | 可能       | 可能   |
| 读已提交 | 不可能 | 可能       | 可能   |
| 可重复读 | 不可能 | 不可能     | 可能   |
| 串行化   | 不可能 | 不可能     | 不可能 |

不可重复读侧重于update这种操作，同一条数据前后读起来不一样的情况,

幻读侧重于insert delete这种操作，前后两次select 数据的数量会发生变化

查看隔离级别

```sql
show variables like '%isolation%';
```

设置隔离级别

```sql
SET [GLOBAL|SESSION] TRANSACTION ISOLATION LEVEL level
```



隔离



### MVCC概念

https://zhuanlan.zhihu.com/p/286775643

多版本并发控制(Multi-Version Concurrency Control, MVCC)，是数据库常用的一种并发控制的手段，用于提供对数据库的并发访问。

`MVCC` 保证了并发事务的安全，实现了读写不冲突。让数据库支持并发，简单就是支持多用户访问数据库中的公共数据

#### 当前读和快照读

- 当前读：会对读取记录进行加锁，读取到的是当前记录的最新版本

  ```sql
  select .... for update;
  insert...
  update...
  detele...
  ```

- 快照读：即单纯的`select`语句，不会对记录进行加锁，是基于MVCC机制，读取的是记录数据的可见版本



#### 并发事务带来的问题

MySQL 是支持多事务并发执行的，否则来一个请求处理一个请求，处理一个人请求的时候，别的人都等着，这网站就别做了，用户都要砸键盘了。

一个事务在写数据的时候，另一个事务要读这行数据，该怎么处理？一个事务在写数据，另一个数据也要写这行数据，又该怎么处理这个冲突？其实吧，为了解决这些问题，MySQL 可以说是煞费苦心，使用了 MVCC 多版本控制机制、事务隔离机制、锁机制等



#### 版本链

对于InnoDB来说，每行记录都包含着两个必要的隐藏列

- trx_id: 一个事务每次对每条聚簇索引记录进行改动时，都会把该事务的事务id赋值给trx_id隐藏列
- roll_pointer: 每次对某条聚簇索引的进行

#### ReadView

- m_ids: 在生成ReadView时，当前系统中活跃的读写事务的事务id列表
- min_trx_id: 在生成ReadView时，当前系统中活跃的读写事务中的最小事务id，也就是m_ids中的最小值
- max_trx_id: 在生成ReadView时，系统应该分配给下一个事务的事务id值
- creator_trx_id: 生成该ReadView的事务id值



| TRANSACTION-A                         | TRANSACTION-B                           | TRANSACTION-C                  | TRANSACTION-D                  |
| :------------------------------------ | :-------------------------------------- | ------------------------------ | ------------------------------ |
| START TRANSACTION                     | START TRANSACTION                       | START TRANSACTION              | START TRANSACTION              |
| update user set name='js' where id =1 |                                         |                                |                                |
|                                       | update user set name='java' where id =1 |                                |                                |
|                                       | commit                                  |                                |                                |
|                                       |                                         | select * from user where id =1 |                                |
| update user set name='go' where id =1 |                                         |                                |                                |
|                                       |                                         | select * from user where id =1 |                                |
|                                       |                                         |                                | select * from user where id =1 |
| commit                                |                                         | commit                         | commit                         |



#### MVCC是否解决幻读

2. 快照读：对于MVCC来说，每次都是从ReadView中读取，不会别的事务提交新插入的记录，解决了幻读的问题
3. 当前读：MVCC无法解决，一般都通过使用 Gap Lock（间隙锁） 或 Next-Key Lock（Gap Lock + Record Lock）来解决。





#### 分配事务id

​       如果某个事务在执行过程中对某个表执行了增删改操作，那么InnoDB就会给它分配一个唯一的事务id。对于只读事务，只有在它第一次对某个用户创建的临时表执行增删改操作时，才会分配事务id；对于读写事务，只有在它第一次对某个表执行增删改操作时，才会分配事务id。有时，虽然我们开启了一个读写事务，但是该事务中都是查询语句，就不会被分配事务id了。


#### 事务id的生成

​       服务器在内存中维护一个全局变量作为事务id，只要分配了一个就会增加1，每当这个变量的值为256的倍数，就将该值刷新到系统空间表中页号为5的页面中一个名为Max_Trx ID的属性中。InnoDB聚簇索引记录的行格式中有个trx_id隐藏列，记录的就是对这个聚簇索引记录进行改动的语句所在的事务id。

















