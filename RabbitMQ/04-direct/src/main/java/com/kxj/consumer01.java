package com.kxj;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author kxj
 * @date 2021/3/7 22:43
 * @desc
 */
public class consumer01 {
    private final static String FANOUT_QUEUE_1 = "direct-queue-1";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.102.218.26");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(FANOUT_QUEUE_1, true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" message: " + message + "'");
        };
        channel.basicConsume(FANOUT_QUEUE_1, true, deliverCallback, consumerTag -> {
        });

    }
}
