package org.example.handler;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.example.constants.RabbitConsts;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 延迟队列处理器
 * </p>
 *
 * @author xie.wei
 * @since 2021/11/14
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitConsts.DELAY_QUEUE)
public class DelayQueueHandler {

    @RabbitHandler
    public void directHandlerManualAck( Message message, Channel channel){
    }
}
