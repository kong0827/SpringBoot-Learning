package com.kxj;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiangjin.kong
 * @date 2021/2/20 10:48
 * <p>
 * <p>
 * 按规则转发消息（最灵活） 转发消息主要是根据通配符。
 * <p>
 * 在这种交换机下，队列和交换机的绑定会定义一种路由模式，那么，通配符就要在这种路由模式和路由键之间匹配后交换机才能转发消息。
 * <p>
 * 路由键必须是一串字符，用句号（.） 隔开，
 * <p>
 * 路由模式必须包含一个 星号（*），主要用于匹配路由键指定位置的一个单词， 井号（#）就表示相当于一个或者多个单词
 */
@Configuration
public class RabbitMqConfig {

    public static final String TOPIC_QUEUE_1 = "topic.queue1";
    public static final String TOPIC_QUEUE_2 = "topic.queue2";
    public static final String ROUTING_KEY_1 = "topic.queue1";
    public static final String ROUTING_KEY_2 = "topic.#";
    public static final String TOPIC_EXCHANGE = "custom.topic";

    /**
     * Topic交换机模式
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE_1, true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE_2, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY_1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY_2);
    }
}
