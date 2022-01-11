package demo.pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import common.Constant;
import java.io.IOException;
import utils.RabbitMQUtil;

/**
 * 订阅模式：pub/sub 生产者
 *
 * @author xie.wei
 * @date created at 2022-01-06 16:11
 */
public class Producer {

    public static void main(String[] args) throws IOException {
        final Connection connection = RabbitMQUtil.getConnection();
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        String message = "registration success";
        channel.basicPublish(Constant.FANOUT_EXCHANGE, "", null, message.getBytes());

        RabbitMQUtil.close(channel, connection);
    }
}
