## Redission

### 为什么需要分布式锁

在传统的单体应用时代，传统的企业级Java应用为了解决并发条件下访问共享资源时出现数据不一致的问题，通常借助JDK自身提供的关键字（`Synchronized`）或者并发工具类(`Lock RetreenLock`)等加以实现 ，控制并发访问问题。

但是现在的企业级应用大多采用的是集群和分布的方式进行部署，将业务拆分成多个子系统，并进行独立部署，通常每个系统会部署多个实例。在性能和效率提升的同时，也带来了一些问题，传统的加锁方式已经不能解决并发访问问题。因为不管是`Synchronized`还是`Lock RetreenLock` 控制并发线程对共享资源的访问只适用于单体应用或者单一部署的服务实例，而这种集群、分布式部署的服务实例一般是部署在不同的机器上，导致它们各自拥有独立的主机、JDK，那么这种跨JVM进程之间访问共享资源，传统传统的锁机制已经不能解决，那么此时需要引入分布式锁。

![image-20210327225016994](E:%5CgithubResp%5CSpringBoot-Demo%5CDistributeLock%5Csrc%5Cmain%5Cresources%5Cimgs%5Cimage-20210327225016994.png)

如上，当多个客户端发起请求，会被Nginx转发到相应的服务，假设它们去操作同一服务不同实例下的成员变量A，A在每个实例上都拥有单独的内存空间，每个请求会修改自己实例中A的值，但是并不会同步到其他实例上。

### 分布式锁

分布式锁，并不是一个中间件或者组件，而是一种机制，一种解决方案。主要是指在分布式部署的环境下，通过锁机制让多个客户端或者多个服务进程互斥的对共享资源进行访问，从而避免出现并发安全问题。

常见的分布式锁的实现有基于数据库级别的乐观锁、悲观锁，基于Redis的原子操作，基于Zookeeper的互斥排它锁，以及基于Redisson的分布式锁。

![image-20210327231337906](E:%5CgithubResp%5CSpringBoot-Demo%5CDistributeLock%5Csrc%5Cmain%5Cresources%5Cimgs%5Cimage-20210327231337906.png)



保证的是AP

### 分布式锁的实现

#### 1、Redis

`Redis` 并没有提供直接的分布式锁组件，而是间接的借助redis的原子操作加以实现。redis之所以能够实现分布式锁，主要是因为redis所采用的单线程机制，不管外部系统发起了多少请求，同一时刻只能有一个线程执行某种操作，其他线程进入等待队列。

基于redis实现分布式锁主要用的是 `SET KEY VALUE [EX seconds] [PX milliseconds] [NX|XX]` 命令

- [EX seconds]：设置`key`的过期时间，单位 秒
- [PX milliseconds]：设置`key` 的过期时间，单位 毫秒
- [NX|XX]：NX: `key`不存在的`value`, 成功返回OK，失败返回nil. XX:`key`存在时设置`value`, 成功返回OK，失败返回nil

![image-20210328002456999](E:%5CgithubResp%5CSpringBoot-Demo%5CDistributeLock%5Csrc%5Cmain%5Cresources%5Cimgs%5Cimage-20210328002456999.png)





代码

模拟的是商品减库存操作

```java
@Autowired
StringRedisTemplate redisTemplate;

/**
     * 模拟商品减库存操作
     * @param productCode
     */
@PutMapping("reduce/{product-code}")
public void reduce(@PathVariable(value = "product-code") String productCode) {
    String lockKey = "lock:" + productCode;
    try {
        Boolean isSuccess = redisTemplate.opsForValue().setIfAbsent(lockKey, productCode, 10, TimeUnit.MILLISECONDS);

        if (isSuccess) {
            // 获取锁成功 执行减库存操作
            Integer count = Integer.parseInt(redisTemplate.opsForValue().get(productCode));
            if (count > 0) {
                redisTemplate.opsForValue().increment(productCode);
            }
        }
    } finally {
        // 释放锁
        redisTemplate.delete(lockKey);
    }
}
```



上述代码虽然能够实现分布式锁，但是仍存在不少的问题。例如

**锁的误解除**

假设有两个线程 线程1和线程2 同时去操作某一共享资源，线程1 获得锁，并设置超时时间为10s，当执行业务流程时，发现已经执行了10s，那么线程1变会释放锁，此时线程2 拿到锁。此时**线程1和线程2并发执行**。 当线程1 执行完，并删除锁的时候，此时线程2未执行完，删除的是线程2所持有的锁。

![image-20210328011001074](E:%5CgithubResp%5CSpringBoot-Demo%5CDistributeLock%5Csrc%5Cmain%5Cresources%5Cimgs%5Cimage-20210328011001074.png)

#### 2、Redisson

Redisson是在redis基础上实现Java驻内存数据网格的综合中间件，之所以Redisson提供了分布式锁，是因为基于Redis的原子操作实现的分布式存在一定的缺陷，而Redisson则很好的弥补了这些缺陷。

这里主要以Spring Boot为基础来整合Redisson

1. pom.xml

   需要引入Redisson的依赖

   ```xml
   <dependencies>
     <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
     </dependency>
   <dependency>
     <groupId>org.redisson</groupId>
       <artifactId>redisson</artifactId>
       <version>3.14.0</version>
     </dependency>
   </dependencies>
   ```

2. 配置文件

   ```yml
   
   ```

   

   



### 配置![image-20210325005028930](C:%5CUsers%5C%E5%B0%8FK%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210325005028930.png)

### 如果保证主从架构锁失效

- zookpeer保证CP
- RedLock
- 人工干预

###  高并发分布式锁提升

-  分段锁