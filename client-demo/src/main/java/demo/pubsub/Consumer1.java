package demo.pubsub;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import common.Constant;
import java.io.IOException;
import utils.RabbitMQUtil;

/**
 * pub/sub consumer1
 * 发送短信服务
 *
 * @author xie.wei
 * @date created at 2022-01-06 16:48
 */
public class Consumer1 {

    static String QUEUE = "fanout_exchange_queue_sms";

    public static void main(String[] args) throws IOException {
        final Connection connection = RabbitMQUtil.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE, false, false, false, null);
        // 定义队列消费者
        channel.queueBind(QUEUE, Constant.FANOUT_EXCHANGE, "");

        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [短信服务] received : " + msg + "!");
            }
        };
        channel.basicConsume(QUEUE, true, consumer);
    }
}
