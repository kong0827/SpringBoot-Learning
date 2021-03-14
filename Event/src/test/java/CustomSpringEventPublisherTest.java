import com.kxj.EventApplication;
import com.kxj.publisher.CustomSpringEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author xiangjin.kong
 * @date 2021/1/15 17:02
 */
@SpringBootTest(classes = EventApplication.class)
public class CustomSpringEventPublisherTest {

    @Autowired
    CustomSpringEventPublisher customSpringEventPublisher;

    @Test
    public void publishMessage() throws IOException {
        customSpringEventPublisher.publishCustomEvent("自定义事件");
        System.in.read();
    }

}
