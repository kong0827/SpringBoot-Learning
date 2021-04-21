package com.kxj.mybatis;

import com.kxj.mybatis.bean.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kxj
 * @date 2021/4/21 23:51
 * @desc
 */
public class StatementHandlerTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;

    @Before
    public void configuration() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(StatementHandlerTest.class.getResourceAsStream("/mybatis-config.xml"));
        sqlSession = sqlSessionFactory.openSession();
    }

    /**
     * 单个参数测试
     */
    @Test
    public void test() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(10);
        System.out.println(user);
    }
}
