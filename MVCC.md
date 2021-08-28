## MVCC 

![image-20210822235520360](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210822235520360.png)

![image-20210822235823300](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210822235823300.png)

![image-20210823001108846](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823001108846.png)

![image-20210823000552676](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823000552676.png)



![image-20210823001737139](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210823001737139.png)

### 提出疑问

开启多个事务，t1,t2,t3

1、开启t1,t2,t3

2、t1更新数据

3、t2读

4、t1提交

5、t3读

6、预测t2, t3读取结果







### MySql 日志

1、undo log  原子性

2、redo log 持久性 





### 四种隔离级别

- Serializable (串行化)：可避免脏读、不可重复读、幻读的发生。
- Repeatable read (可重复读)：可避免脏读、不可重复读的发生。
- Read committed (读已提交)：可避免脏读的发生。
- Read uncommitted (读未提交)：最低级别，任何情况都无法保证。



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





### 概念

https://zhuanlan.zhihu.com/p/286775643

多版本并发控制(Multi-Version Concurrency Control, MVCC)，是数据库常用的一种并发控制的手段，用于提供对数据库的并发访问。





### MVCC作用

`MVCC` 保证了并发事务的安全，实现了读写不冲突。让数据库支持并发，简单就是支持多用户访问数据库中的公共数据



#### 当前读和快照读





#### 并发事务带来的问题

MySQL 是支持多事务并发执行的，否则来一个请求处理一个请求，处理一个人请求的时候，别的人都等着，这网站就别做了，用户都要砸键盘了。

一个事务在写数据的时候，另一个事务要读这行数据，该怎么处理？一个事务在写数据，另一个数据也要写这行数据，又该怎么处理这个冲突？其实吧，为了解决这些问题，MySQL 可以说是煞费苦心，使用了 MVCC 多版本控制机制、事务隔离机制、锁机制等

#### 事务的隔离级别

| 隔离级别 | 脏读   | 不可重复读 | 幻读   |
| -------- | ------ | ---------- | ------ |
| 未提交读 | 可能   | 可能       | 可能   |
| 读已提交 | 不可能 | 可能       | 可能   |
| 可重复读 | 不可能 | 不可能     | 可能   |
| 串行化   | 不可能 | 不可能     | 不可能 |



查看隔离级别

```sql
SHOW VARIABLE LIKE 'transaction-isolation'
```

设置隔离级别

```sql
SET [GLOBAL|SESSION] TRANSACTION ISOLATION LEVEL level
```



#### MVCC是否解决幻读





### 分配事务id

​       如果某个事务在执行过程中对某个表执行了增删改操作，那么InnoDB就会给它分配一个唯一的事务id。对于只读事务，只有在它第一次对某个用户创建的临时表执行增删改操作时，才会分配事务id；对于读写事务，只有在它第一次对某个表执行增删改操作时，才会分配事务id。有时，虽然我们开启了一个读写事务，但是该事务中都是查询语句，就不会被分配事务id了。


#### 事务id的生成

​       服务器在内存中维护一个全局变量作为事务id，只要分配了一个就会增加1，每当这个变量的值为256的倍数，就将该值刷新到系统空间表中页号为5的页面中一个名为Max_Trx ID的属性中。InnoDB聚簇索引记录的行格式中有个trx_id隐藏列，记录的就是对这个聚簇索引记录进行改动的语句所在的事务id。

















