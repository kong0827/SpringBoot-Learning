package com.kxj.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author kxj
 * @date 2021/3/8 23:47
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Autowired
    TopicProducer producer;

    @Test
    public void test() {
        producer.producer();
    }
}
