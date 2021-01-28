package com.kxj;

import cn.hutool.core.lang.Dict;
import com.kxj.controller.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiangjin.kong
 * @date 2021/1/28 11:04
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestControllerTest {

    @Autowired
    TestController testController;

    @Test
    public void test() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 30; i++) {
            executorService.submit(() -> {
                Dict dict = testController.test1();
                System.out.println(dict);
            });
        }
    }
}
