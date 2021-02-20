package com.kxj.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiangjin.kong
 * @date 2021/2/20 10:48
 */
@Configuration
public class RabbitMqConfig {

    public static final String QUEUE = "queue";

    /**
     * direct交换机模式
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }
}
