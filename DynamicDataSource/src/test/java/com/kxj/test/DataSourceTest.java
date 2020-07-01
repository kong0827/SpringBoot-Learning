package com.kxj.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * @author xiangjin.kong
 * @date 2020/7/1 11:19
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DataSourceTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void test() {
        System.out.println(dataSource);
    }
}
