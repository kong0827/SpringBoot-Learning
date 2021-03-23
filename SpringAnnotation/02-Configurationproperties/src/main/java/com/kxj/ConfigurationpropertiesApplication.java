package com.kxj;

import com.kxj.config.MailConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author xiangjin.kong
 * @date 2021/3/23 19:29
 */
@SpringBootApplication
public class ConfigurationpropertiesApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ConfigurationpropertiesApplication.class, args);
        MailConfiguration mailConfiguration = (MailConfiguration) run.getBean("mailConfiguration");
        System.out.println(mailConfiguration);
    }
}
