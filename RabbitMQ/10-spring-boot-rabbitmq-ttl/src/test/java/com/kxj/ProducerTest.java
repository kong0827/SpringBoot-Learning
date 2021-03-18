package com.kxj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author kxj
 * @date 2021/3/17 23:49
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Autowired
    RabbitProducer producer;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        producer.producer();
    }

    @Test
    public void test1() {
        producer.producer2();
    }

    @Test
    public void test2() {
        producer.producer3();
    }

    @Test
    public void test3() {
    }
}
