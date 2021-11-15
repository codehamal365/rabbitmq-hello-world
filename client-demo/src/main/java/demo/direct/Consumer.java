package demo.direct;

import com.rabbitmq.client.*;
import common.Constant;
import utils.RabbitMQUtil;

import java.io.IOException;

/**
 *  第一种模型(直连)
 *
 * @author xie.wei
 * @date created at 2021-11-15 22:43
 */
public class Consumer {
    public static void main(String[] args) {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(Constant.DIRECT_QUEUE, false, false, false, null);

            /**
             * 参数明细：
             * 1、queue 队列名称
             * 2、autoAck 自动回复，当消费者接收到消息后要告诉mq消息已接收，
             * 如果将此参数设置为tru表示会自动回复mq，如果设置为false要通过编程实现回复
             * 3、callback，消费方法，当消费者接收到消息要执行的方法
             */
            channel.basicConsume(Constant.DIRECT_QUEUE, true, new DefaultConsumer(channel) {

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
                    // key,本例即队列名称
                    String routingKey = envelope.getRoutingKey();
                    // 交换机
                    String exchange = envelope.getExchange();
                    // 消息id，mq在channel中用来标识消息的id，可用于确认消息已接收
                    long deliveryTag = envelope.getDeliveryTag();
                    String msg = new String(body, "utf-8");
                    System.out.printf("routingKey:%s,exchange:%s,deliveryTag:%s,body:%s", routingKey,
                            exchange, deliveryTag, msg);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // RabbitMQUtil.close(channel, connection);
        }
    }
}
