## MySQL

### 1、B树与B+树的区别

![image-20210425112531740](https://gitee.com/kongxiangjin/images/raw/master/img/20210425112533.png)

![image-20210425112741321](https://gitee.com/kongxiangjin/images/raw/master/img/20210425112742.png)

B树(B 即 Banlance 多路平衡查找树)

1. 每个节点都存储key和data，所有节点组成这个树，并且叶子节点指针都为null
2. 索引字段没有冗余，任何一个关键字只出现在一个节点中
3. 搜索可能在非叶子节点结束

B+树

1. 只有叶子节点存储data，叶子节点包含了这颗树所有的键值，叶子节点不存储指针，非叶子节点不存储数据
2. B+树上增加了顺序访问指针，也就是每个叶子节点增加一个指向相邻叶子节点的指针，适合范围查找
3. 索引字段有冗余，关键字
4. 搜索必须到叶子节点





### 2、MyISAM和InnoDB

![image-20210425140050191](https://gitee.com/kongxiangjin/images/raw/master/img/20210425140051.png)

![image-20210425140029558](https://gitee.com/kongxiangjin/images/raw/master/img/20210425140031.png)

**InnoDB会生成两个文件**

1、.frm  表定义，描述表结构

2、.ibd  索引 + 数据



**MyISAM会生成三个文件**

1、.frm  表定义，描述表结构

2、.MYD "D"数据信息文件，是表的数据文件

3、.MYI   "I"索引信息文件，是表数据文件中任何索引的数据树。



根节点是在内存中的，然后加载磁盘中的节点到内存中

​	1. innodb支持事务，myisam不支持

2. innodb支持外键、myisam不支持

3. innodb支持表锁和行锁，但是myisam支持表锁

4. innodb在5.6版本之后支持全文索引

5. innodb索引的叶子节点直接存放数据，而myisam存放地址





### 3、InnoDB可以放多少主键+指针

https://cloud.tencent.com/developer/article/1443681

![image-20210425123035820](https://gitee.com/kongxiangjin/images/raw/master/img/20210425123037.png)



我们先将数据记录按主键进行排序，分别存放在不同的页中（为了便于理解这里一个页中只存放 3 条记录，实际情况可以存放很多），**除了存放数据的页以外，还有存放键值+指针的页**，如图中page number=3 的页，该页存放键值和指向数据页的指针，这样的页由 N 个键值 + 指针组。

InnoDB存储引擎最小存储单位的，叫做页(Page)，默认一页为16kb，假如一行数据大小是1k，那么理论上一页就可以放16条数据

主键Id类型int占4字节，bigint占8字节，指针在InnoDB中占6字节，这样算下来就是16384/14 = 1170，即每页可以放1170个指针。

一个指针指向一个存放记录的页，一个页里可以放16条数据，那么一颗高度为2的B+树就可以存放 1170 * 16=18720 条数据。同理，高度为3的B+树，就可以存放 1170 * 1170 * 16 = 21902400 条记录。

**所以在 InnoDB 中 B+ 树高度一般为 1-3 层，它就能满足千万级的数据存储**。



### 4、MySQL 的索引要使用 B+ 树而不是其它结构？

**B 树**

**因为 B 树不管叶子节点还是非叶子节点，都会保存数据**，这样导致在非叶子节点中能保存的指针数量变少（有些资料也称为扇出），指针少的情况下要保存大量数据，只能增加树的高度，导致 IO 操作变多，查询性能变低

**红黑树**

树层级过深，导致IO过多

**Hash**

没有范围查找，易出现Hash冲突



### 什么是聚集索引

https://zoyi14.smartapps.cn/pages/note/index?slug=8991cbca3854&origin=share&hostname=baiduboxapp&_swebfr=1

InnoDB有两大类型的索引，簇集索引和普通索引

聚集索引的叶子节点存储行记录，因此InNoDB有且只有一个聚集索引。

​	1、如果定义了Pk，PK就是聚集索引

​	2、如果没有定义PK，第一个`Not Null unique` 列就是聚集索引

​	3、如果都没有，InNoDB默认创建一个隐藏的 `row_id` 作为聚集索引

InnoDB的普通索引的叶子节点储存主键值



### 为什么InnoDB要建主键，推荐自增的整数主键

为什么建主键？

如果设置了主键，那么InnoDB会选择主键作为聚集索引

为什么使用自增主键？

聚簇索引的数据的物理存放顺序与索引顺序是一致的**，即：**只要索引是相邻的，那么对应的数据一定也是相邻地存放在磁盘上的。键的顺序按照数据记录的插入顺序排列，自动有序。当一页写满，就会自动开辟一个新的页

聚簇索引的顺序和磁盘中数据的存储顺序是一致的，如果主键不是自增id，那么可以想 象，它会干些什么，`不断地调整数据的物理地址、分页`，当然也有其他一些措施来减少这些操作，但却无法彻底避免。但，如果是自增的，那就简单了，它只需要一 页一页地写，索引结构相对紧凑，磁盘碎片少，效率也高



### 为什么非主键索引结构叶子结点存储的是主键值

**1. 保持一致性：**
当数据库表进行DML操作时，同一行记录的页地址会发生改变，因非主键索引保存的是主键的值，无需进行更改。

**2. 节省存储空间：**
Innodb数据本身就已经汇聚到主键索引所在的B+树上了， 如果普通索引还继续再保存一份数据，就会导致有多少索引就要存多少份数据。



### MySQL 事务

### MySQL 的隔离级别

### 5、什么是回表查询，覆盖索引

**回表查询：**

先定位主键值，再定位行记录。即先通过普通索引定位到主键值，然后回表扫描主键的索引树，定位到行记录

**覆盖索引：**

只需要在一棵索引书上就能获取SQL所需要的的所有列数据，无需回表操作，速度更快



**如何实现覆盖索引：**

将被查询的字段，建立到联合索引中

```sql
create table user(
	id int primary key,
    name varchar(255),
    sex varchar(2),
    index(name)
) engine = innodb;


select id,name from user where name = 'zhangsan';
能够命中name索引，索引叶子节点存储了主键Id,通过name的索引树既可以获得id和name,无需回表，符合覆盖索引


select id,name,sex from user where name = 'zhangsan';
会产生回表操作，因为name索引树上无法获得sex字段，会回表到主键索引树查找行记录获得sex的值，不符合索引覆盖

```



**如何利用索引覆盖优化SQL**

```sql
create table user(
	id int primary key,
    name varchar(255),
    sex varchar(2),
    index(name)
) engine = innodb;
```

- 全表count查询优化

  ```sql
  select count(name) from user;
  不能利用索引覆盖。
  添加索引：
  alter table user add key(name);
  就能够利用索引覆盖提效
  ```

- 列查询回表优化

  ```sql
  单列索引(name)升级为联合索引(name, sex)，即可避免回表
  ```

- **分页查询**

  ```sql
  select id,name,sex ...order by name limit 500,100;
  将单列索引(name)升级为联合索引(name, sex)，也可以避免回表
  ```

  



### 6、update加什么锁

1. 如果where条件是索引字段，加的是行锁，如果是非索引字段，加的是表锁
2. 如果where条件是索引字段，但是区分度不高，mysql优化默认是非索引字段，加的也是表锁

