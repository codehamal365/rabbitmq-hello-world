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
 * working queue consumer one
 *
 * @author xie.wei
 * @date created at 2022-01-06 15:22
 */
public class Consumer1 {
    public static void main(String[] args) {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(Constant.WORKING_QUEUE, false, false, false, null);

            /*
             * 参数明细：
             * 1、queue 队列名称
             * 2、autoAck 自动回复，当消费者接收到消息后要告诉mq消息已接收，
             * 如果将此参数设置为tru表示会自动回复mq，如果设置为false要通过编程实现回复
             * 3、callback，消费方法，当消费者接收到消息要执行的方法
             */
            channel.basicConsume(Constant.WORKING_QUEUE, true, new DefaultConsumer(channel) {

                /**
                 * 当接收到消息后此方法将被调用
                 * @param consumerTag  消费者标签，用来标识消费者的，
                 *        在监听队列时设置channel.basicConsume
                 * @param envelope 信封，通过envelope
                 * @param properties 消息属性
                 * @param body 消息内容
                 * @throws IOException
                 */
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
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // RabbitMQUtil.close(channel, connection);
        }
    }
}
