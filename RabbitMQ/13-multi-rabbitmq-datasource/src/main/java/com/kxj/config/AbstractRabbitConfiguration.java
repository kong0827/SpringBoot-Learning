package com.kxj.config;

import lombok.Data;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

/**
 * @author kongxiangjin
 */
@Data
public abstract class AbstractRabbitConfiguration {
    // protected String address;
    protected String host;
    protected Integer port;
    protected String userName;
    protected String password;
    protected String virtualHost;
    protected String queueName;
    protected String exchangeName;
    protected String acknowledge = "manual";
    protected Integer prefetch = 1;
    protected String routingKey;

    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(Boolean.TRUE);
        return connectionFactory;
    }
}