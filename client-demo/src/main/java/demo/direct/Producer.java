package demo.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import common.Constant;
import utils.RabbitMQUtil;

/**
 * @author xie.wei
 * @date created at 2021-11-15 17:39
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();

            channel.queueDeclare("test", false, false, false,
                    null);

            channel.basicPublish("", Constant.DIRECT_TOPIC, null,
                    "hello world".getBytes());

        } finally {
            RabbitMQUtil.close(channel, connection);
        }
    }
}
