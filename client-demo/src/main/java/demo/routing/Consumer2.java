package demo.routing;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import common.Constant;
import java.io.IOException;
import utils.RabbitMQUtil;

/**
 * routing key路由模式 exchange type: direct
 *
 * @author xie.wei
 * @date created at 2022-01-06 17:23
 */
public class Consumer2 {

    static String QUEUE = "direct_exchange_queue_email";

    public static void main(String[] args) throws IOException {
        final Connection connection = RabbitMQUtil.getConnection();
        final Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE, false, false, false, null);
        // 绑定交换机,同时指定需要指定的routing key,可以同时指定多个
        channel.queueBind(QUEUE, Constant.DIRECT_EXCHANGE, "email");

        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [邮件服务] received : " + msg + "!");
            }
        };
        // 监听队列，自动ACK
        channel.basicConsume(QUEUE, true, consumer);
    }
}
