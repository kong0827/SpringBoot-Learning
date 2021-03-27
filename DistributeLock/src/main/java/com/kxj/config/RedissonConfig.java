package com.kxj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kxj
 * @date 2021/3/25 1:00
 * @desc
 */
@Configuration
public class RedissonConfig {

    /**
     * 单机 主从
     */
    @Bean
    public Redisson redisson() {

    }
}
