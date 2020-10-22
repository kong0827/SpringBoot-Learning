package com.kxj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws Exception {
        System.out.println(dataSource.getClass());
    }
}
