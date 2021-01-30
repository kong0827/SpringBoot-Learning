import com.kxj.EventApplication;
import com.kxj.publisher.ApplicationEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author xiangjin.kong
 * @date 2021/1/15 16:45
 */
@SpringBootTest(classes = EventApplication.class)
public class PublishEventTest {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ApplicationContext context;

    @Test
    public void publishMessage() {
        applicationEventPublisher.publish("Message 1");

        /**
         * 项目启动失败事件监听消息发布
         */
        RuntimeException exception = new RuntimeException("项目启动失败");
        SpringApplication application = new SpringApplication();
        this.context.publishEvent(new ApplicationFailedEvent(application, new String[0],
                (ConfigurableApplicationContext) this.context, exception));
    }

}
