import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author kxj
 * @date 2021/3/6 21:23
 * @desc 队列的消费者
 */
public class consumer01 {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.102.218.26");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /**
         * 注意
         * Rabbitmq服务通道是持久通道,该queue 已经存在, 而且通道属性需要保持一致
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

//        DefaultConsumer consumer = new DefaultConsumer(channel) {
//            /**
//             *
//             * @param consumerTag 标识
//             * @param envelope 获取的一些信息 包括交换机 路由
//             * @param properties 配置信息
//             * @param body 数据
//             * @throws IOException
//             */
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println("consumerTag:" + consumerTag);
//                System.out.println("envelope:" + envelope);
//                System.out.println("properties:" + properties);
//                System.out.println("message:" + new String(body));
//            }
//        };
//        channel.basicConsume(QUEUE_NAME, true, consumer);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" message: " + message + "'");
        };
        /**
         * 启动一个消费者，并返回服务端生成的消费者标识
         * queue:队列名
         * autoAck：true 接收到传递过来的消息后acknowledged（应答服务器），false 接收到消息后不应答服务器
         * deliverCallback： 当一个消息发送过来后的回调接口
         * cancelCallback：当一个消费者取消订阅时的回调接口;取消消费者订阅队列时除了使用{@link Channel#basicCancel}之外的所有方式都会调用该回调方法
         * @return 服务端生成的消费者标识
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

    }
}
