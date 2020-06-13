package com.kxj.mybatis;

import org.apache.ibatis.session.Configuration;
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

    @Before
    public void configuration() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = sqlSessionFactoryBuilder.build(SecondCacheTest.class.getResourceAsStream("mybatis-config.xml"));
        configuration = factory.getConfiguration();
    }

    @Test
    public void test() {

    }
}
