import com.kxj.EventApplication;
import com.kxj.publisher.ApplicationEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiangjin.kong
 * @date 2021/1/22 11:35
 */
@SpringBootTest(classes = EventApplication.class)
public class AsyncApplicationEventPublisherTest {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void publishTest() throws InterruptedException {
        applicationEventPublisher.publish("发布消息");
        Thread.sleep(1000);
    }
}
