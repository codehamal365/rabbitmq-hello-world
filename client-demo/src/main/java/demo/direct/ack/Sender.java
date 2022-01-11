package demo.direct.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xie.wei
 * @since 2021/11/13
 */
public class Sender {
    private static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        final Connection connection = RabbitMQUtil.getConnection();
        // 创建通道、生产者和mq服务所有通信都在channel通道中完成
        final Channel channel = connection.createChannel();
        // 声明队列
        // 参数：String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        /**
         * 1.queue 队列名称
         * 2.durable 是否持久化、如果持久化，mq重启后队列还在，不会保证消息的持久化
         * 3.exclusive 是否独占连接，队列只允许在该连接中访问，如果connection连接关闭队列
         *   则自动删除，如果将此参数设置为true可以用于临时队列的创建
         * 4.autoDelete 自动删除，队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数
         *   设置为true可以实现临时队列（队列不用了就自动删除）
         *   是否在消费完成后自动删除队列
         * 5.参数，可以设置一个队列的扩展参数，比如：可设置存活时间
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 消息内容
        String message = "Hello world";
        // 向指定队列发送消息
        //参数：String exchange, String routingKey, BasicProperties props, byte[] body
        /**
         * 1.exchange 交换机，如果不指定将使用mq默认交换机(设置为"")
         * 2.routingKey 路由key，交换机根据key来将消息转发到指定队列，如果使用默认
         *   默认交换机，routingKey设置为队列的名称
         * 3.props 消息属性，这里可是设置消息的持久化
         * 4.body 消息内容
         */
        // props MessageProperties.PERSISTENT_TEXT_PLAIN  持久化消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        //关闭通道和连接(资源关闭最好用try-catch-finally语句处理)
        channel.close();
        connection.close();
    }
}
