package com.kxj.message;

import com.kxj.entity.Receiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xiangjin.kong
 * @date 2020/4/16 11:13
 * @desc
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ReceiverMessageTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverMessageTest.class);

    @Autowired
    Receiver receiver = new Receiver();

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() throws InterruptedException {
        while (receiver.getCount() == 0) {
            try {
                LOGGER.info("Sending message...");
                stringRedisTemplate.convertAndSend("chat", "Hello from Redis!");

                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
