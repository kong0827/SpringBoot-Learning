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
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();
            /**
             *
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             *
             * queue：队列名称
             * durable：是否持久化，当mq重启后，消息还在
             * exclusive：
             *      1、是否独占，只能有一个消费者监听这队列
             *      2、当Connection关闭时，是否删除队列
             * autoDelete：是否自动删除。当没有Consumer时，自动删除掉
             * arguments：参数
             */

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 使用默认的交换器，路由键需要设置队列名
            for (int i = 0; i < 10; i++) {
                channel.basicPublish("", QUEUE_NAME, null, (i+"hello world").getBytes());
            }
            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
