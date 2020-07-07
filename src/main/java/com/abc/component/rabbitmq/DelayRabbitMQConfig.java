package com.abc.component.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayRabbitMQConfig {

    @Bean
    public Queue OrderQueue() {
        return new Queue("OrderQueue", true);
    }

    @Bean
    public DirectExchange OrderDirectExchange() {
        return new DirectExchange("OrderExchange");
    }

    @Bean
    public Binding BindingOrderDirectExchange() {
        return BindingBuilder.bind(OrderQueue()).to(OrderDirectExchange()).with("Order");
    }

    @Bean
    public Queue OrderDelayQueue() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", "OrderExchange");
        params.put("x-dead-letter-routing-key", "Order");
        return new Queue("OrderDelayQueue", true, false, false, params);
    }

    @Bean
    public DirectExchange OrderDelayDirectExchange() {
        return new DirectExchange("OrderDelayExchange");
    }

    @Bean
    public Binding BindingOrderDelayDirectExchange() {
        return BindingBuilder.bind(OrderDelayQueue()).to(OrderDelayDirectExchange()).with("OrderDelay");
    }

}