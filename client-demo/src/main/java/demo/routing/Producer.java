package demo.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import common.Constant;
import java.io.IOException;
import utils.RabbitMQUtil;

/**
 * routing路由模式 交换机类型为direct
 *
 * @author xie.wei
 * @date created at 2022-01-06 17:15
 */
public class Producer {
    public static void main(String[] args) throws IOException {
        final Connection connection = RabbitMQUtil.getConnection();
        final Channel channel = connection.createChannel();
        // 声明exchange,指明类型为direct
        channel.exchangeDeclare(Constant.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 消息内容
        String message = "注册成功！请短息回复[T]退订";
        // 发送消息，并指定routing key 为sms，只有短信服务才能接受该消息
        channel.basicPublish(Constant.DIRECT_EXCHANGE, "sms", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        RabbitMQUtil.close(channel, connection);
    }
}
