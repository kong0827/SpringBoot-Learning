import com.kxj.ConditionApplication;
import com.kxj.manager.MessageManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiangjin.kong
 * @date 2021/3/23 19:25
 */

@SpringBootTest(classes = ConditionApplication.class)
@RunWith(SpringRunner.class)
public class MessageManagerTest {

    @Autowired
    MessageManager messageManager;

    @Test
    public void test() {
        messageManager.send();
    }
}
