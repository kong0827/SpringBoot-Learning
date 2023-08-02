package com.kxj.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author kxj
 * @date 2023/8/2 23:20
 * @desc
 */
@Configuration
@ConfigurationProperties(prefix = "kxj.rabbit")
public class RabbitConfig extends AbstractRabbitConfiguration {

    @Bean("primaryConnectionFactory")
    @Primary
    public ConnectionFactory primaryConnectionFactory() {
        return super.connectionFactory();
    }

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(@Qualifier("primaryConnectionFactory") ConnectionFactory connectionFactory,
                                         @Qualifier("confirmCallback") ConfirmCallback confirmCallback,
                                         @Qualifier("returnCallback") ReturnCallback returnCallback) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean(name = "primaryContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("primaryConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 设置ACK确认机制
        factory.setAcknowledgeMode(AcknowledgeMode.valueOf(acknowledge.toUpperCase()));
        // 设置消费者消费条数
        factory.setPrefetchCount(prefetch);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean(name = "primaryRabbitAdmin")
    public RabbitAdmin rabbitAdmin(@Qualifier("primaryConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);

        // 声明交换机，队列及对应绑定关系
        Queue queue = RabbitmqUtil.createQueue(queueName);
        TopicExchange exchange = RabbitmqUtil.createTopicExchange(exchangeName);
        Binding binding = RabbitmqUtil.createBinding(queue, exchange, routingKey);
        RabbitmqUtil.createRabbitAdmin(queue, exchange, binding, rabbitAdmin);
        return rabbitAdmin;
    }
}
