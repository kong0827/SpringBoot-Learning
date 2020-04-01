package com.kxj.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kxj
 * @date 2020/4/1 22:36
 * @desc
 */
@Configuration
public class CustomViewResolverConfig {

    @Bean
    public MyCustomViewResolver getCustomViewResolver() {
        return new MyCustomViewResolver();
    }
}
