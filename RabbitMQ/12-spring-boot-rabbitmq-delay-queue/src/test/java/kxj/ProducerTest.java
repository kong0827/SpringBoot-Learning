package kxj;

import kxj.producer.RabbitProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

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
     * 延迟队列
     */
    @Test
    public void test() throws IOException {
        rabbitProducer.producer("hello 延迟队列");
        System.in.read();
    }


}
