package com.kxj.mybatis;

import com.kxj.mybatis.bean.User;
import org.apache.ibatis.executor.*;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tommy
 * @title: ExecutorTest
 * @projectName coderead-mybatis
 * @description: TODO
 * @date 2020/5/3012:10 AM
 */
public class ExecutorTest2 {


    private Configuration configuration;
    private Connection connection;
    private JdbcTransaction jdbcTransaction;
    private MappedStatement ms;
    private SqlSessionFactory factory;

    @Before
    public void init() throws SQLException {
        // 获取构建器
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        // 解析XML 并构造会话工厂
        factory = factoryBuilder.build(ExecutorTest.class.getResourceAsStream("/mybatis-config.xml"));
        configuration = factory.getConfiguration();
        jdbcTransaction = new JdbcTransaction(factory.openSession().getConnection());
        // 获取SQL映射
        ms = configuration.getMappedStatement("com.kxj.mybatis.UserMapper.selectById");
    }




}
