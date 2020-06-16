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
 * @author xiangjin.kong
 * @date 2020/6/5 0:33
 * @desc
 */
public class ExecutorTest {
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

    // 简单执行器测试
    @Test
    public void simpleTest() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
        List<Object> list = executor.doQuery(ms, 10, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));

        executor.doQuery(ms, 10, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
        System.out.println(list.get(0));
    }

    // 可重用执行器
    @Test
    public void reuseStatementTest() throws SQLException {
        ReuseExecutor reuseExecutor = new ReuseExecutor(configuration, jdbcTransaction);
        List<Object> list = reuseExecutor.doQuery(ms, 10, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
        // 相同的SQL 会缓存对应的PrepareStatement   缓存周期=>会话
        List<Object> list2 = reuseExecutor.doQuery(ms, 10, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
        System.out.println(list.get(0));
    }

    // 批处理执行器
    @Test
    public void batchStatement() throws SQLException {
        BatchExecutor batchExecutor = new BatchExecutor(configuration, jdbcTransaction);
        MappedStatement mappedStatement = configuration.getMappedStatement("com.kxj.mybatis.UserMapper.setName");
        Map<String, Object> map = new HashMap();
        map.put("arg0", 10);
        map.put("arg1", "kxj go up");
        batchExecutor.doUpdate(mappedStatement, map);
        batchExecutor.doUpdate(mappedStatement, map);
        batchExecutor.doFlushStatements(false);
    }

    // 二級缓存
    @Test
    public void twoCacheTest() throws SQLException {
        SimpleExecutor simpleExecutor = new SimpleExecutor(configuration, jdbcTransaction);

        // 装饰器模式
        CachingExecutor cachingExecutor = new CachingExecutor(simpleExecutor);

        cachingExecutor.query(ms, 10, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);

        // 二级缓存提交后 生效
        cachingExecutor.commit(true);
        cachingExecutor.query(ms, 10, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    }

    @Test
    public void sqlSessionTest() {
        // 默认使用简单执行器
        SqlSession sqlSession = factory.openSession(true);
        List<User> list = sqlSession.selectList("com.kxj.mybatis.UserMapper.selectById", 10);
        List<User> list1 = sqlSession.selectList("com.kxj.mybatis.UserMapper.selectById3", 10);
//        for (User user : list) {
//            System.out.println(user);
//        }
    }

    /**
     * 可重用执行器
     */
    @Test
    public void reuseExecutorTest() {
        // 相同的SQL会被缓存
        SqlSession sqlSession = factory.openSession(ExecutorType.REUSE, true);
        List<User> list = sqlSession.selectList("com.kxj.mybatis.UserMapper.selectById", 10);
        List<User> list1 = sqlSession.selectList("com.kxj.mybatis.UserMapper.selectById3", 10);
    }

    /**
     * 批处理执行器
     */
    @Test
    public void batchExecutorTest() {
        SqlSession sqlSession = factory.openSession(ExecutorType.BATCH, true);
//        List<User> list = sqlSession.selectList("com.kxj.mybatis.UserMapper.selectById", 10);
//        List<User> list1 = sqlSession.selectList("com.kxj.mybatis.UserMapper.selectById3", 10);

        // sql相同,MapperStatement相同,必須是连续的，则会使用一个Statement
        // 批处理中如果执行查询方法，则会调用flushStatement,和数据库进行传输
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.setName(10, "hahaha");
        mapper.addUser(Mock.newUser());
        mapper.addUser(Mock.newUser());

        List<BatchResult> batchResults = sqlSession.flushStatements();
        System.out.println(batchResults);

    }
}
