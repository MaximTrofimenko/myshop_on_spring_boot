package com.trofimenko.myshop.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Value("${myshop.rabbitmq.exchange}")
    private String EXCHANGE;

    @Value("${myshop.rabbitmq.routingkey}")
    private String ROUTING_KEY;

    @Value("${myshop.rabbitmq.queue}")
    private String QUEUE;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE, true, false, false);
    }

    //связывает exchange и queue
    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }

}