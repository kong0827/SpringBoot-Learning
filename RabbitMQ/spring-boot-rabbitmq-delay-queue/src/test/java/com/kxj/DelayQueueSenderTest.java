package com.kxj;

import com.kxj.send.MessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author xiangjin.kong
 * @date 2021/2/23 15:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMQApplication.class)
public class DelayQueueSenderTest {

    @Autowired
    MessageSender messageSender;

    @Test
    public void test() throws IOException {
        messageSender.sendMessage("test_queue_1","hello i am delay msg");
        System.in.read();
    }
}
