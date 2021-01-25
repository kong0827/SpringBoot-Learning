import com.kxj.EventApplication;
import com.kxj.publisher.CustomSpringEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiangjin.kong
 * @date 2021/1/15 17:02
 */
@SpringBootTest(classes = EventApplication.class)
public class CustomSpringEventPublisherTest {

    @Autowired
    CustomSpringEventPublisher customSpringEventPublisher;

    @Test
    public void publishMessage() {
        customSpringEventPublisher.publishCustomEvent("自定义事件");
    }

}
