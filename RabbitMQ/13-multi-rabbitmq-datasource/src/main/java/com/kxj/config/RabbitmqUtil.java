package com.kxj.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

import java.util.Objects;

/**
 * @author kongxiangjin
 */
public class RabbitmqUtil {

    public static DirectExchange createDirectExchange(String exchangeName) {
        if (StringUtils.isNotBlank(exchangeName)) {
            return new DirectExchange(exchangeName, true, false);
        }
        return null;
    }
    public static TopicExchange createTopicExchange(String exchangeName) {
        if (StringUtils.isNotBlank(exchangeName)) {
            return new TopicExchange(exchangeName, true, false);
        }
        return null;
    }

    public static FanoutExchange createFanoutExchange(String exchangeName) {
        if (StringUtils.isNotBlank(exchangeName)) {
            return new FanoutExchange(exchangeName, true, false);
        }
        return null;
    }

    public static Queue createQueue(String queueName) {
        if (StringUtils.isNotBlank(queueName)) {
            return new Queue(queueName, true);
        }
        return null;
    }

    public static Binding createBinding(Queue queueName, Exchange exchangeName, String routingKeyName) {
        if (Objects.nonNull(queueName) && Objects.nonNull(exchangeName)) {
            return BindingBuilder.bind(queueName).to(exchangeName).with(routingKeyName).noargs();
        }
        return null;
    }

//    public static void createRabbitAdmin(Queue queue, DirectExchange exchange, Binding binding, RabbitAdmin rabbitAdmin) {
//        rabbitAdmin.declareQueue(queue);
//        rabbitAdmin.declareExchange(exchange);
//        rabbitAdmin.declareBinding(binding);
//    }

    public static void createRabbitAdmin(Queue queue, Exchange exchange, Binding binding, RabbitAdmin rabbitAdmin) {
        if (queue != null) {
            rabbitAdmin.declareQueue(queue);
        }
        if (exchange != null) {
            rabbitAdmin.declareExchange(exchange);
        }
        if (binding != null) {
            rabbitAdmin.declareBinding(binding);
        }
    }
}