package demo.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import common.Constant;
import java.io.IOException;
import utils.RabbitMQUtil;

/**
 * topics模式
 *
 * @author xie.wei
 * @date created at 2022-01-06 17:41
 */
public class Producer {
    public static void main(String[] args) throws IOException {
        final Connection connection = RabbitMQUtil.getConnection();
        final Channel channel = connection.createChannel();
        // 声明exchange，指定类型为topic
        channel.exchangeDeclare(Constant.TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
        // 消息内容
        String message = "这是一只行动迅速的橙色的兔子";
        // 发送消息，并且指定routing key为：quick.orange.rabbit
        channel.basicPublish(Constant.TOPIC_EXCHANGE, "quick.orange.rabbit", null, message.getBytes());
        System.out.println(" [动物描述：] Sent '" + message + "'");
        RabbitMQUtil.close(channel, connection);
    }
}
