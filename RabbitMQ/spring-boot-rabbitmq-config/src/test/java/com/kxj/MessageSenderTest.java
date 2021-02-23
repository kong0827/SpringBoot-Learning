package com.kxj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.kxj.sender.MessageSender;

import java.io.IOException;

/**
 * @author xiangjin.kong
 * @date 2021/2/22 16:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMQApplication.class)
public class MessageSenderTest {

    @Autowired
    private MessageSender messageSender;
    @Value("${log.exchange}")
    private String exchange;
    @Value("${log.info.binding-key}")
    private String routingKey;

    /**
     * 测试失败通知
     */
    @Test
    public void test() throws IOException {
        for (int i = 0; i < 3; i++) {
            String message = "this is error message " + i;
            messageSender.convertAndSend(exchange, "test", message);
        }
        System.in.read();
    }

    /**
     * 测试发布者确认
     */
    @Test
    public void sendInfoMsg() throws IOException, InterruptedException {
        for (int i = 0; i < 3; i++) {
            String message = "this is info message " + i;
            messageSender.convertAndSend(exchange, routingKey, message);
        }
        System.in.read();
    }
}
