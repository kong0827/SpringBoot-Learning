import com.kxj.RabbitMQApplication;
import com.kxj.producer.MessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiangjin.kong
 * @date 2021/2/22 14:53
 */
@SpringBootTest(classes = RabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class MessageProducerTest {

    @Autowired
    MessageProducer messageProducer;

    @Test
    public void test() throws InterruptedException {
        messageProducer.producer();
        Thread.sleep(1000);
    }
}
