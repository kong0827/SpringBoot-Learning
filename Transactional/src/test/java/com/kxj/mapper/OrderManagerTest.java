package com.kxj.mapper;

import com.kxj.manager.OrderManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xiangjin.kong
 * @date 2020/11/2 15:20
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderManagerTest {

    @Autowired
    OrderManager orderManager;

    @Test
    public void createOrderTest() {

        orderManager.createOrder("测试User4", "测试Product4");

    }
}
