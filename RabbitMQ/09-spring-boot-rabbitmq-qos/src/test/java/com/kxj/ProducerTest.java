package com.kxj;

import com.kxj.producer.MqProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author kxj
 * @date 2021/3/15 23:20
 * @desc
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Autowired
    MqProducer producer;

    @Test
    public void test() throws IOException, InterruptedException {
        for (int i = 0; i < 300; i++) {
            producer.topicProducer();
        }
        Thread.sleep(20000);
    }
}
