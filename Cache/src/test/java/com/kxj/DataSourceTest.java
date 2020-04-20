package com.kxj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

/**
 * @author xiangjin.kong
 * @date 2020/4/20 10:19
 * @desc
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DataSourceTest {

    @Autowired
    DataSource datasource;

    @Test
    public void test() {
        System.out.println(datasource);
    }
}
