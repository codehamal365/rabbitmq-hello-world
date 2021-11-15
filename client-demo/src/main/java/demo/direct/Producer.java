package demo.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import common.Constant;
import utils.RabbitMQUtil;

/**
 * 第一种模型(直连)
 *
 * @author xie.wei
 * @date created at 2021-11-15 17:39
 */
public class Producer {

    public static void main(String[] args) {
        // 获取连接
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = null;
        try {
            // 创建通道、生产者和mq服务所有通信都在channel中完成
            channel = connection.createChannel();

            /**
             *  声明 direct 直连队列
             *  1. queue 队列名称
             *  2. durable 队列是否持久化，mq重启队列还在，不会保证消息的持久化
             *  3. exclusive 是否独占连接，队列只允许在连接中访问，如果连接关闭则队列无法
             *  访问,如果将此参数设置为true可以用于临时队列的创建
             *  4. autoDelete 自动删除,及队列不在使用时自动删除，一般消费者消费完成ACK确认后，
             *  生产消费都断开了连接后，队列自动删除，
             *  5. 参数，可以设置队列的扩展属性，比如：可设置存活时间等
             */
            channel.queueDeclare(Constant.DIRECT_QUEUE, false, false, false,
                    null);

            /**
             * 发布消息
             * 1. exchange 交换机
             * 2. routingKey 路由key，交换机根据key将消息转发到指定队列，如果使用默认交换机
             * 则key就是队列名称
             * 3. props 消息属性，可以设置消息为可持久化，MessageProperties.PERSISTENT_TEXT_PLAIN
             * 4. body 消息内容
             */
            channel.basicPublish("", Constant.DIRECT_QUEUE, null,
                    "hello world".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭通道和连接
            RabbitMQUtil.close(channel, connection);
        }
    }
}
