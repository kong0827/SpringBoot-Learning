package com.kxj.mybatis;

import com.kxj.mybatis.bean.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Property;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kxj
 * @date 2020/6/11 23:48
 * @desc 二级缓存测试
 */
public class SecondCacheTest {

    Configuration configuration = null;
    SqlSessionFactory factory = null;

    @Before
    public void configuration() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        factory = sqlSessionFactoryBuilder.build(SecondCacheTest.class.getResourceAsStream("/mybatis-config.xml"));
        configuration = factory.getConfiguration();
    }

    @Test
    public void test() {
        // 需要开启二级缓存 @CacheNamespace
        Cache cache = configuration.getCache("com.kxj.mybatis.UserMapper");
        cache.putObject("kxj", "hahaha....");
        Object object = cache.getObject("kxj");
        System.out.println(object);
    }

    /**
     * 缓存的扩展：更改缓存的实现方式（默认是PerpetualCache）
     */
    @Test
    public void test2() {
       /* @CacheNamespace(implementation = DiskCache.class, properties = {@Property(name = "cachePath",
                value ="E:\\githubResp\\SpringBoot-Demo\\mybatis\\src\\main\\resources" )}) //更换实现方式*/
        Cache cache = configuration.getCache("UserMapper");
        cache.putObject("cache store", "hahaha....");

    }

    /**
     * 缓存的扩展 更改溢出淘汰策略（默认是LruCache）
     */
    @Test
    public void test3() {
        // @CacheNamespace(eviction = FifoCache.class, size = 10) // 设置缓存淘汰策略
        Cache cache = configuration.getCache("UserMapper");
        for (int i = 0; i < 12; i++) {
            cache.putObject("kxj:" + i, i);
        }
    }

    /**
     * 序列化
     */
    @Test
    public void test4() {
        // 在默认序列化的情况下是false
        // 设置不走序列化 @CacheNamespace(readWrite=false)  true
        Cache cache = configuration.getCache("UserMapper");
        cache.putObject("user", Mock.newUser());
        Object user = cache.getObject("user");
        Object user1 = cache.getObject("user");
        System.out.println(user == user1);


    }

    /**
     * 过期清理，默认 1 小时
     */
    @Test
    public void test5() throws InterruptedException {
        // @CacheNamespace(flushInterval = 10000)
        Cache cache = configuration.getCache("UserMapper");
        cache.putObject("user", "hello");
        System.out.println(cache.getObject("user"));
        Thread.sleep(11000);
        System.out.println(cache.getObject("user"));
    }

    /**
     * 二级缓存命中条件
     * 1、必须提交
     */
    @Test
    public void cacheTest() {
        SqlSession sqlSession = factory.openSession();
        sqlSession.getMapper(UserMapper.class).selectById(10);
        sqlSession.commit();
        SqlSession sqlSession1 = factory.openSession();
        sqlSession1.getMapper(UserMapper.class).selectById(10);

    }
}
