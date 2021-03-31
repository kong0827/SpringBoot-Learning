import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author xiangjin.kong
 * @date 2021/3/29 19:41
 */
@SpringBootApplication
public class RedissonApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RedissonApplication.class, args);
        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
