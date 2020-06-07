package com.kxj.mybatis;

import com.kxj.mybatis.bean.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.UUID;

/**
 * @author tommy
 * @title: JDBC
 * @projectName test
 * @description: TODO
 * @date 2020/5/119:28 PM
 */
public class JdbcTest {
    public static final String URL = "jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    private Connection connection;

    @Before
    public void init() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @After
    public void over() throws SQLException {
        connection.close();
    }

    /**
     * 预编译
     *
     * @throws SQLException
     */
    @Test
    public void jdbcTest() throws SQLException {
        /**
         *   注册数据库驱动(现在提供SPI无需注册驱动)
         *   获得数据库连接
         *   获取Statement
         *   执行SQL
         *   设置参数
         *   遍历结果集
         */
        String sql = "select * from users where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "kxj");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            user.setId(id);
            user.setName(name);
            System.out.println(user.getId() + ":" + user.getName());
        }
        resultSet.close();
        preparedStatement.close();
    }

    /**
     * 批处理
     */
    @Test
    public void prepareBatchTest() throws SQLException {
        String sql = "insert into users(name, age) value(?, 18)";
        PreparedStatement ps = connection.prepareStatement(sql);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            ps.setString(1, UUID.randomUUID().toString());
//            ps.execute();   // 单条插入
            ps.addBatch();
        }
        ps.executeBatch();
        System.out.println(System.currentTimeMillis() - startTime);
        ps.close();
    }

    @Test
    public void injectTest() throws SQLException {
        String name = "kxj";
        selectByName(name);

        String temp = "kxj' or '1'='1";
        selectByName(temp);

    }

    /**
     * SQL 注入
     */
    public void selectByName(String name) throws SQLException {
        String sql = "select * from users where name = '" + name + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        System.out.println(count);
    }



}
