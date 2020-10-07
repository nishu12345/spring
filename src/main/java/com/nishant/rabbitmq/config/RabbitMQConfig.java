package com.nishant.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.nishant.rabbitmq.util.Constants.*;

@Configuration
public class RabbitMQConfig {

    @Bean
    DirectExchange testExchange() {
        return new DirectExchange(TEST_EXCHANGE);
    }

    @Bean
    DirectExchange testDeadLetterExchange() {
        return new DirectExchange(TEST_DEAD_LETTER_EXCHANGE);
    }

    @Bean
    Queue empQueue() {
        return QueueBuilder.durable(EMP_QUEUE)
                           .withArgument("x-dead-letter-exchange", TEST_DEAD_LETTER_EXCHANGE)
                           .withArgument("x-dead-letter-routing-key", EMP_DEAD_LETTER_ROUTING_KEY)
                           .build();
    }

    @Bean
    Queue empDeadLetterQueue() {
        return QueueBuilder.durable(EMP_DEAD_LETTER_QUEUE)
                           .build();
    }

    @Bean
    Binding empBinding() {
        return BindingBuilder.bind(empQueue())
                             .to(testExchange())
                             .with(EMP_QUEUE_ROUTING_KEY);
    }

    @Bean
    Binding empDeadLetterBinding() {
        return BindingBuilder.bind(empDeadLetterQueue())
                             .to(testDeadLetterExchange())
                             .with(EMP_DEAD_LETTER_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
