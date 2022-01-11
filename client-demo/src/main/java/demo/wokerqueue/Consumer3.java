package demo.wokerqueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import common.Constant;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import utils.RabbitMQUtil;

/**
 * working queue consumer three
 * 实现能者多劳 需手动ack
 *
 * @author xie.wei
 * @date created at 2022-01-06 15:22
 */
public class Consumer3 {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtil.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(Constant.WORKING_QUEUE, false, false, false, null);
        // 设置每个消费者同时只能处理一条消息，手动ack下生效
        channel.basicQos(1);
        channel.basicConsume(Constant.WORKING_QUEUE, false, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body, "utf-8");
                System.out.println(" [消费者1] received : " + msg + "!");
                //模拟任务耗时1s
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }
}
