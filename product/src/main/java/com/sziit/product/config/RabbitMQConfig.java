package com.sziit.product.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "order.exchange";
    public static final String QUEUE = "order.seckill.queue";
    public static final String ROUTING_KEY = "order.seckill";
    @Bean
    public Exchange orderExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }
    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ROUTING_KEY).noargs();
    }
} 