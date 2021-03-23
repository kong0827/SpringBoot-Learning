package com.kxj;

import com.kxj.producer.RabbitProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author kxj
 * @date 2021/3/20 22:12
 * @desc
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Autowired
    RabbitProducer rabbitProducer;

    /**
     * 超时
     */
    @Test
    public void test() {
        rabbitProducer.producer("hello 死信队列");
    }

    /**
     * 队列消息长度到达限制
     */
    @Test
    public void test2() {
        for (int i = 0; i < 30; i++) {

            rabbitProducer.producer("hello 死信队列");
        }
    }

    /**
     * 消息拒收
     */
    @Test
    public void test3() {
        rabbitProducer.producer("hello 消费者拒收");
    }
}
