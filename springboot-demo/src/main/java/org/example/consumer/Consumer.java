package org.example.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MessageDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xie.wei
 * @since 2022-01-10
 */
@Component
@Slf4j
public class Consumer {

    @RabbitListener(queues = "queue.direct.one")
    void receiveDirectExchangeMsgFromQueueOne(MessageDto messageDto, Message message, Channel channel) {
        log.info("received direct exchange msg from queue.direct.one >>>>>  {}", messageDto);
    }

    @RabbitListener(queues = "queue.direct.two")
    void receiveDirectExchangeMsgFromQueueTwo(MessageDto messageDto, Message message, Channel channel) {
        log.info("received direct exchange msg from queue.direct.two >>>>>  {}", messageDto);
    }

    @RabbitListener(queues = {"queue.fanout.one", "queue.fanout.two"})
    void receiveFanoutExchangeMsg(MessageDto messageDto, Message message, Channel channel) {
        log.info("received fanout exchange msg >>>>>  {}", messageDto);
    }

    @RabbitListener(queues = {"queue.topic.one", "queue.topic.two"})
    void receiveTopicExchangeMsg(MessageDto messageDto, Message message, Channel channel) {
        log.info("received topic exchange msg >>>>>  {}", messageDto);
    }

    @RabbitListener(queues = {"queue.header.one", "queue.header.two"})
    void receiveHeaderExchangeMsg(MessageDto messageDto, Message message, Channel channel) {
        log.info("received topic exchange msg >>>>>  {}", messageDto);
    }
}
