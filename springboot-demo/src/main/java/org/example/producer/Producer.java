package org.example.producer;

import lombok.RequiredArgsConstructor;
import org.example.dto.MessageDto;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xie.wei
 * @since 2022-01-10
 */
@RestController
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate template;
    private final DirectExchange directExchange;
    private final FanoutExchange fanoutExchange;
    private final TopicExchange topicExchange;
    private final HeadersExchange headersExchange;

    @PostMapping("/sendDirectExchangeTopic")
    public void sendDirectExchangeTopic(@RequestBody MessageDto messageDto) {
        // 这里不设置exchange,会用默认的exchange
        template.convertAndSend(directExchange.getName(), messageDto.getRoutingKey(), messageDto);
    }

    @PostMapping("/sendFanoutExchangeTopic")
    public void sendFanoutExchangeTopic(@RequestBody MessageDto messageDto) {
        // 这里不设置exchange,会用默认的exchange
        template.convertAndSend(fanoutExchange.getName(), "", messageDto);
    }

    @PostMapping("/sendTopicExchangeTopic")
    public void sendTopicExchangeTopic(@RequestBody MessageDto messageDto) {
        // 这里不设置exchange,会用默认的exchange
        template.convertAndSend(topicExchange.getName(), messageDto.getRoutingKey(), messageDto);
    }

    @PostMapping("/sendHeaderExchangeTopic")
    public void sendHeaderExchangeTopic(@RequestBody MessageDto messageDto) {
        // 这里不设置exchange,会用默认的exchange
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("color", messageDto.getHeaderValue());
        MessageConverter messageConverter = new Jackson2JsonMessageConverter();
        Message message = messageConverter.toMessage(messageDto, messageProperties);
        template.send(headersExchange.getName(), "", message);
    }
}
