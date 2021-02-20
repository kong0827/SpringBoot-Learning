package com.kxj;

import com.kxj.send.MQSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiangjin.kong
 * @date 2021/2/20 16:37
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DirectMQTest {

    @Autowired
    private MQSender sender;

    @Test
    public void test() throws InterruptedException {
        sender.send("hello direct exchange");
        Thread.sleep(1000);

    }
}
