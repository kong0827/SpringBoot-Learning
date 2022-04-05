

## Spring Boot 整合Mongodb

### 是否应该使用MongoDB

从目前阿里云MongoDB云数据库上的用户看，MongoDB 的应用已经渗透到各个领域，比如**游戏、物流、电商、内容管理、社交、物联网、视频直播等**，以下是几个实际的应用案例。

- 游戏场景，使用 MongoDB 存储游戏用户信息，用户的装备、积分等直接以内嵌文档的形式存储，方便查询、更新。

- 物流场景，使用 MongoDB 存储订单信息，订单状态在运送过程中会不断更新，以MongoDB内嵌数组的形式来存储，一次查询就能将订单所有的变更读取出来。

- 社交场景，使用 MongoDB 存储存储用户信息，以及用户发表的朋友圈信息，通过地理位置索引实现附近的人、地点等功能。

- 物联网场景，使用 MongoDB 存储所有接入的智能设备信息，以及设备汇报的日志信息，并对这些信息进行多维度的分析。

- 视频直播，使用 MongoDB 存储用户信息、礼物信息等。

- ……

  如果你还在为是否应该使用MongoDB，不如来做几个选择题来辅助决策（注：以下内容改编自MongoDB公司TJ同学的某次公开技术分享）。

  | 应用特征                                           | YES / NO |
  | :------------------------------------------------- | :------- |
  | 应用不需要事务及复杂 join 支持                     | 必须 Yes |
  | 新应用，需求会变，数据模型无法确定，想快速迭代开发 | ？       |
  | 应用需要2000-3000以上的读写QPS（更高也可以）       | ？       |
  | 应用需要TB甚至 PB 级别数据存储                     | ?        |
  | 应用发展迅速，需要能快速水平扩展                   | ?        |
  | 应用要求存储的数据不丢失                           | ?        |
  | 应用需要99.999%高可用                              | ?        |
  | 应用需要大量的地理位置查询、文本查询               | ？       |

  如果上述有1个 Yes，可以考虑 MongoDB，2个及以上的 Yes，选择 MongoDB 绝不会后悔



### 常用注解

- @Document:标示映射到Mongodb文档上的领域对象
- @Id:标示某个域为ID域
- @Indexed:标示某个字段为Mongodb的索引字段



### 基本用法

继承MongoRepository接口可以获得常用的数据操作方法, 同JPA， 不赘述

```java
@Repository
public interface CustomerRepository extends MongoRepository<Customer, Integer> {

   Customer findByName(String name);
}
```



### 事务

从版本 4 开始，MongoDB 支持[Transactions](https://www.mongodb.com/transactions)。[事务建立在Sessions](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.sessions)之上，因此需要一个活跃的`ClientSession`

- 要支持多文档事务，mongo版本必须为4.0以上
- 开启replica-set（多文档事务必须要在replica-set的基础上才能开启，MongoDB官网上有描述），具体怎么开启可参考此文： [windows下配置replica-set](https://www.jianshu.com/p/fe2c9e149db0)

`MongoTransactionManager`是众所周知的 Spring 事务支持的网关。它允许应用程序使用[Spring 的托管事务特性](https://docs.spring.io/spring/docs/5.3.17/spring-framework-reference/html/transaction.html)。将`MongoTransactionManager`a 绑定`ClientSession`到线程。`MongoTemplate`检测会话并相应地对与事务关联的这些资源进行操作。`MongoTemplate`还可以参与其他正在进行的交易

```java
@Transactional(rollbackFor = Exception.class)
public void testTransaction(Customer customer) {
    customerRepository.save(customer);
    int i = 1 / 0;
}
```

若没有开启事务配置，上述数据会插入数据库，事务不生效

```java
@Configuration
public class MongoConfig {
    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory factory){
      return new MongoTransactionManager(factory);
    }
}
```

注：以上为MongoTemplate方式，MongoTemplate和MongoRepository方式都与Spring整合SpringDataMongoDB相同



### 删除_class列

默认插入数据时会加上"_class"列

```json
{  "_id": 
   {    "$oid": "6249a7fd5883a044b0de7298"  },  
   "name": "kxj", 
   "age": 18,  
   "_class": "com.kxj.entity.Customer"
}
```

删除如下

方法一

```java
@Configuration
public class MongoDBConfig implements InitializingBean {

    @Autowired
    @Lazy
    private MappingMongoConverter mappingMongoConverter;

    @Override
    public void afterPropertiesSet() throws Exception {
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
}
```

```java
@Bean("converter")
public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory,
                                                   MongoMappingContext context, MongoCustomConversions conversions) {
    DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
    MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver,
            context);
    mappingConverter.setCustomConversions(conversions);
    //不设置_class字段
    MongoTypeMapper mongoTypeMapper = new DefaultMongoTypeMapper(null);
    mappingConverter.setTypeMapper(mongoTypeMapper);
    return mappingConverter;
}
```



### 聚合查询

MongoDB 中的聚合用于处理数据并返回计算结果。数据分阶段处理，一个阶段的输出作为下一个阶段的输入提供。这种分阶段应用转换和对数据进行计算的能力使聚合成为非常强大的分析工具。

*Spring Data MongoDB 使用三个类Aggregation*为原生聚合查询提供了抽象，其中 Aggregation 包装了一个聚合查询，*AggregationOperation*包装了各个管道阶段，*AggregationResults*是聚合产生的结果的容器。

*要执行和聚合，首先，使用Aggregation*类上的静态构建器方法创建聚合管道，然后使用*Aggregation*类上的*newAggregation()*方法创建*Aggregation*的实例，最后使用*MongoTemplate*运行聚合：

```java
MatchOperation matchStage = Aggregation.match(new Criteria("foo").is("bar"));
ProjectionOperation projectStage = Aggregation.project("foo", "bar.baz");
        
Aggregation aggregation 
  = Aggregation.newAggregation(matchStage, projectStage);

AggregationResults<OutType> output 
  = mongoTemplate.aggregate(aggregation, "foobar", OutType.class);
```

*MatchOperation*和*ProjectionOperation*都实现了*AggregationOperation*。其他聚合管道也有类似的实现。*OutType*是预期输出的数据模型。

现在，我们将看一些示例及其解释，以涵盖主要的聚合管道和运算符。

#### 分组

```java
@Data
@ToString
@FieldNameConstants
public class CustomerGroupDTO {

    // @Id注释将 group中_id 字段从输出映射到模型中的状态：
    // 也可以通过andExpression完成"_id"的映射
    //  ProjectionOperation projectionOperation = Aggregation.project().andExpression("_id").as(CustomerGroupDTO.Fields.age);
    // _id是分组字段
    @Id
    private Integer age;

    private BigDecimal totalMoney;
} 

/**
     * 根据年龄进行分组，求各年龄段总金额
     * 按照年龄降序，求总金额大于800的
     */
public void group() {
        GroupOperation groupOperation =     Aggregation.group(Customer.Fields.age).sum(Customer.Fields.money).as(CustomerGroupDTO.Fields.totalMoney);
        MatchOperation matchOperation = Aggregation.match(Criteria.where(CustomerGroupDTO.Fields.totalMoney).gt(800));
        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, Customer.Fields.age));
        Aggregation aggregation = Aggregation.newAggregation(groupOperation, matchOperation, sortOperation);
        AggregationResults<CustomerGroupDTO> aggregate = mongoTemplate.aggregate(aggregation, Customer.class, CustomerGroupDTO.class);
        List<CustomerGroupDTO> groupDTOList = aggregate.getMappedResults();
        for (CustomerGroupDTO customerGroupDTO : groupDTOList) {
            System.out.println(customerGroupDTO.toString());
        }
    }
```

*AggregationResults*类实现了*Iterable*，因此我们可以对其进行迭代并打印结果。

如果输出数据模型未知， 可以使用标准的 MongoDB 类*Document* 





#### 分组最大值最小值

```
 /**
     * 求年龄段人数分布的最小值和最大值
     *
     */
    public void groupAndAvg() {
        GroupOperation groupOperation = Aggregation.group(Customer.Fields.age).count().as(CustomerGroupDTO.Fields.ageCount);
        SortOperation sortByCount = Aggregation.sort(Sort.Direction.ASC, CustomerGroupDTO.Fields.ageCount);
//        ProjectionOperation projectionOperation = Aggregation.project()
        GroupOperation groupOperation1 = Aggregation.group().first("_id").as(CustomerGroupDTO.Fields.minAge).first(CustomerGroupDTO.Fields.ageCount).as(CustomerGroupDTO.Fields.minAgeCount)
                .last("_id").as(CustomerGroupDTO.Fields.maxAge).last(CustomerGroupDTO.Fields.ageCount).as(CustomerGroupDTO.Fields.maxAgeCount);

        Aggregation aggregation = Aggregation.newAggregation(groupOperation, sortByCount, groupOperation1);
        AggregationResults<CustomerGroupDTO> aggregate = mongoTemplate.aggregate(aggregation, Customer.class, CustomerGroupDTO.class);
        for (CustomerGroupDTO mappedResult : aggregate.getMappedResults()) {
            System.out.println(mappedResult.toString());
        }
    }
```

需要指定_id映射到那个字段

Aggregation.group().first("_id").as(CustomerGroupDTO.Fields.minAge)

若是在根据那个分组后面获取则不需要"_id"指定, 直接写字段名即可

Aggregation.group().first(CustomerGroupDTO.Fields.age).as(CustomerGroupDTO.Fields.age)

#### 平均







1、聚合查询
分组
排序
case-when
事务配置
下划线与驼峰互转

JOIN







### 读写分离

### 写入多个切片才成功





参考

https://github.com/eugenp/tutorials/tree/master/persistence-modules/spring-data-mongodb

