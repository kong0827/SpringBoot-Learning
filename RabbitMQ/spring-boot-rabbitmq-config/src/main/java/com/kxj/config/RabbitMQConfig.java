package com.kxj.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @author xiangjin.kong
 * @date 2021/2/22 15:45
 *
 * JavaConfig⽅便⾃定义各种属性，⽐如同时配置多个virtual host等
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory(@Value("${spring.rabbitmq.host}") String host,
                                               @Value("${spring.rabbitmq.port}") int port,
                                               @Value("${spring.rabbitmq.username}") String username,
                                               @Value("${spring.rabbitmq.password}") String password,
                                               @Value("${spring.rabbitmq.virtual-host}") String vhost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ReturnCallback returnCallback, ConfirmCallback confirmCallback) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        // 要想使 returnCallback 生效，必须设置为true
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }
}
