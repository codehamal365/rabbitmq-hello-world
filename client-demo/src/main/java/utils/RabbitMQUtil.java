package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import common.Constant;

/**
 * @author xie.wei
 * @since 2021/11/13
 */
public class RabbitMQUtil {

    static final String HOST = Constant.MQ_HOST;
    static final ConnectionFactory factory;

    static {
        // 定义连接工厂
        factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost(HOST);
        // 端口
        factory.setPort(5672);
        // 设置账号信息、用户名、密码
        // 设置虚拟机，一个mq服务可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
        factory.setVirtualHost("/ems");
        factory.setUsername("ems");
        factory.setPassword("ems");
    }

    public static Connection getConnection() {
        try {
            // 通过工厂获取连接
           return factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Channel channel, Connection connection) {
        try {
            if (channel != null) channel.close();
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RabbitMQUtil() {

    }

}
