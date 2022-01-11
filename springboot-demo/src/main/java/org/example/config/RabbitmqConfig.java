package org.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 主题模式绑定分列模式和延时队列
 * https://github.com/lanbai1993/spring-boot-demo/blob/f10dc0a45be84e89acb48f6cf259620f52838b6e/demo-mq-rabbitmq/src/main/java/com/xkcoding/mq/rabbitmq/config/RabbitMqConfig.java#L111
 *
 * 可以用于测试
 *
 * {@link org.springframework.amqp.core.AnonymousQueue}
 *
 * @author xie.wei
 * @since 2022-01-10
 */
@Configuration
public class RabbitMQConfig {

    // direct exchange start //
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("exchange.direct");
    }

    @Bean
    Queue queueOne() {
        return new Queue("queue.direct.one");
    }

    @Bean
    Queue queueTwo() {
        return new Queue("queue.direct.two");
    }

    @Bean
    Binding bindingDirectQueueOne(DirectExchange directExchange, Queue queueOne) {
        return BindingBuilder.bind(queueOne).to(directExchange).withQueueName();
    }

    @Bean
    Binding bindingDirectQueueTwo(DirectExchange directExchange, Queue queueTwo) {
        return BindingBuilder.bind(queueTwo).to(directExchange).withQueueName();
    }
    // direct exchange end //

    // fanout exchange start //
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("exchange.fanout");
    }

    @Bean
    Queue queueFanoutOne() {
        return new Queue("queue.fanout.one");
    }

    @Bean
    Queue queueFanoutTwo() {
        return new Queue("queue.fanout.two");
    }

    @Bean
    Binding bindingFanoutQueueOne(FanoutExchange fanoutExchange, Queue queueFanoutOne) {
        return BindingBuilder.bind(queueFanoutOne).to(fanoutExchange);
    }

    @Bean
    Binding bindingFanoutQueueTwo(FanoutExchange fanoutExchange, Queue queueFanoutTwo) {
        return BindingBuilder.bind(queueFanoutTwo).to(fanoutExchange);
    }
    // fanout exchange end //

    // topic exchange start //

    /**
     * 主题模式队列
     * <li>路由格式必须以 . 分隔，比如 user.email 或者 user.aaa.email</li>
     * <li>通配符 * ，代表一个占位符，或者说一个单词，比如路由为 user.*，那么 user.email 可以匹配，但是 user.aaa.email 就匹配不了</li>
     * <li>通配符 # ，代表一个或多个占位符，或者说一个或多个单词，比如路由为 user.#，那么 user.email 可以匹配，user.aaa.email 也可以匹配</li>
     */
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("exchange.topic");
    }

    @Bean
    Queue queueTopicOne() {
        return new Queue("queue.topic.one");
    }

    @Bean
    Queue queueTopicTwo() {
        return new Queue("queue.topic.two");
    }

    @Bean
    Binding bindingTopicQueueOne(TopicExchange topicExchange, Queue queueTopicOne) {
        return BindingBuilder.bind(queueTopicOne).to(topicExchange).with("*.orange.*");
    }

    @Bean
    Binding bindingTopicQueueTwo(TopicExchange topicExchange, Queue queueTopicTwo) {
        return BindingBuilder.bind(queueTopicTwo).to(topicExchange).with("*.*.rabbit");
    }

    @Bean
    Binding bindingTopicQueueTwoAnother(TopicExchange topicExchange, Queue queueTopicTwo) {
        return BindingBuilder.bind(queueTopicTwo).to(topicExchange).with("kind.orange.#");
    }
    // topic exchange end //

    // header exchange start //
    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange("exchange.headers");
    }

    @Bean
    Queue queueHeaderOne() {
        return new Queue("queue.header.one");
    }

    @Bean
    Queue queueHeaderTwo() {
        return new Queue("queue.header.two");
    }

    @Bean
    Binding bindingHeaderQueueOne(HeadersExchange headersExchange, Queue queueHeaderOne) {
        return BindingBuilder.bind(queueHeaderOne).to(headersExchange)
                .where("color").matches("red");
    }

    @Bean
    Binding bindingHeaderQueueTwo(HeadersExchange headersExchange, Queue queueHeaderTwo) {
        return BindingBuilder.bind(queueHeaderTwo).to(headersExchange).
                where("color").matches("blue");
    }
    // header exchange end //

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherReturns(true);
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
