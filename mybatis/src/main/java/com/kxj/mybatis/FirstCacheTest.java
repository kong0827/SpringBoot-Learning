package com.kxj.mybatis;

import com.kxj.mybatis.bean.User;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author kxj
 * @date 2020/6/9 22:51
 * @desc
 */
public class FirstCacheTest {
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;

    @Before
    public void init() {
        // 获取构建器
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(FirstCacheTest.class.getResourceAsStream("/mybatis-config.xml"));
        sqlSession = sqlSessionFactory.openSession();
    }

    /**
     * 运行时参数相关
     * 1、同一个会话
     * 2、sql语句相同，参数相同
     * 3、相同的StatementId
     * 4、RowBounds相同
     */
    @Test
    public void test1() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(10);
        User user1 = mapper.selectById(10);
        System.out.println(user == user1);  // true
//
//        // 会话不相同
//        UserMapper userMapper = sqlSessionFactory.openSession().getMapper(UserMapper.class);
//        User user2 = userMapper.selectById(10);
//        System.out.println(user == user2); // false
//
//        // sql语句不同，参数不同
//        User user3 = mapper.selectById(11);
//        System.out.println(user == user3); // false
//
//        // statementId不同
//        // com.kxj.mybatis.UserMapper.selectById
//        // com.kxj.mybatis.UserMapper.selectById3
//        User user4 = mapper.selectById3(11);
//        System.out.println(user == user4); // false
//
//        // RowBounds不同 默认 RowBounds.DEFAULT
//        RowBounds rowBounds = new RowBounds(0, 10);
//        List<Object> list = sqlSession.selectList("com.kxj.mybatis.UserMapper.selectById",
//                10, rowBounds);
//        System.out.println(list.get(0) == user); // false
//
//        List<Object> list1 = sqlSession.selectList("com.kxj.mybatis.UserMapper.selectById",
//                10, RowBounds.DEFAULT);
//        System.out.println(list1.get(0) == user); // true
    }

    /**
     * 操作与配置相关
     * 1、未手动清空缓存(提交、回滚)
     * 2、未配置flushCache = true
     * 3、未执行Update
     * 4、缓存的作用域不是STATEMENT
     */
    @Test
    public void test2() {
        // 手动清空缓存
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectById(10);

        // 提交
        sqlSession.commit();
        // 或者 sqlSession.rollback();

        User user1 = userMapper.selectById(10);
        System.out.println(user == user1);  // false


        // 配置flushCache = true
        // @Options(flushCache = Options.FlushCachePolicy.TRUE)
        User user2 = userMapper.selectById3(10);
        User user3 = userMapper.selectById3(10);
        System.out.println(user2 == user3);  // false

        // 执行update操作
        User user4 = userMapper.selectById(10);

        userMapper.setName(10, "kxj");

        User user5 = userMapper.selectById(10);
        System.out.println(user4 == user5);  // false

        // 缓存的作用域是STATEMENT
        // <setting name="localCacheScope" value="STATEMENT"/>
        User user6 = userMapper.selectById(10);
        User user7 = userMapper.selectById(10);
        System.out.println(user6 == user7);  // false

    }

}
