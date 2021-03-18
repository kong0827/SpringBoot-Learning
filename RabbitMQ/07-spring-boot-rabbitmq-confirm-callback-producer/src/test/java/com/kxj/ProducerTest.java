package com.kxj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author kxj
 * @date 2021/3/10 0:04
 * @desc
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProducerTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 测试发送成功
     */
    @Test
    public void confirmCallbackSuccessTest() {
        rabbitTemplate.convertAndSend("exchange-7", "test", "hello");
    }

    /**
     * 测试发送失败
     */
    @Test
    public void confirmCallbackFailTest() throws IOException {
        rabbitTemplate.convertAndSend("exchange--", "test", "hello");
        System.in.read();
    }

    /**
     * 测试发送失败
     */
    @Test
    public void returnCallbackFailTest() throws IOException {
        rabbitTemplate.convertAndSend("exchange-7", "1-1", "hello");
        System.in.read();
    }
}
