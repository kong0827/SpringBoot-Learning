package com.kxj;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author kxj
 * @date 2021/3/4 0:29
 * @desc hello-world
 */
public class producer {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("47.102.218.26");
        connectionFactory.setPort(5672);
        try (Connection connection = connectionFactory.newConnection();){
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 使用默认的交换机，则路由需要和队列名保持一致
            channel.basicPublish("", QUEUE_NAME, null, "hello world".getBytes());
            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
