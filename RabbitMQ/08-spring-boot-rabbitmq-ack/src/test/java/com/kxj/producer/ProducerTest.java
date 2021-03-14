package com.kxj.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author kxj
 * @date 2021/3/14 23:24
 * @desc
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Autowired
    RabbitProducer producer;

    @Test
    public void test() throws IOException {
        producer.directProducer();
        producer.topicProducer();
        System.in.read();
    }
}
