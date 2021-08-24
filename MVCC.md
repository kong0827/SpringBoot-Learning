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

