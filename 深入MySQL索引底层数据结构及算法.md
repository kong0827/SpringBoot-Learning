## 深入MySQL索引底层数据结构及算法

### 概念

索引是帮助MySQL高效获取数据的排好序的数据结构

- 二叉树：可能会出现链表

- 红黑树：二叉平衡树，数据量大，树的高度

- Hash: 

  对索引的key进行一次hash计算就可以定位出数据存储的位置

  很多时候Hash索引要比B+树索引更加高效

  仅能满足 ‘=‘，’in'，不支持范围查找

  hash冲突问题

- B-TREE: 树的高度相对B+树更高，I/O会更多

- B+TREE



一页16KB

Bigint  8个字节

索引     6个字节

16KB/(8+6)B = 1170个元素



![image-20210916235646966](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210916235646966.png)

根节点常驻内存

1170 * 1170 * 16 = 2千多万



cd /usr/local/mysql/data



### MyIsam和InnoDB

**myiasm**

- frm文件：表结构信息，mysql8 已取消
- MYD文件：数据文件
- MYI文件：索引文件

**innodb**

- frm文件：表结构信息，mysql8 已取消
- ibd文件：



聚集索引和非聚集索引

- 集聚索引

  叶节点包含了完整的数据记录

- 非聚集索引



### 为什么推荐使用整型的自增索引

- 避免页分裂
- 避免再平衡







```sql
-- 显示Mysql优化后的SQL
show warning;

```

 

index索引效率不高

全表扫描就是扫描聚簇索引

覆盖索引就是一种查询方式，不需要回表，通过一个索引树能获取所要查询的数据