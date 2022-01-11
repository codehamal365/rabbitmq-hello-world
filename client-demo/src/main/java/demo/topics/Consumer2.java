package demo.topics;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import common.Constant;
import java.io.IOException;
import utils.RabbitMQUtil;

/**
 * topics模式
 *
 * @author xie.wei
 * @date created at 2022-01-06 17:46
 */
public class Consumer2 {
    static String QUEUE_NAME = "topic_exchange_queue_Q2";

    public static void main(String[] args) throws IOException {
        final Connection connection = RabbitMQUtil.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列到交换机，同时指定需要订阅的routing key。订阅关于兔子以及懒惰动物的消息
        channel.queueBind(QUEUE_NAME, Constant.TOPIC_EXCHANGE, "*.*.rabbit");
        channel.queueBind(QUEUE_NAME, Constant.TOPIC_EXCHANGE, "lazy.＃");

        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [消费者2] received : " + msg + "!");
            }
        };
        // 监听队列，自动ACK
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
