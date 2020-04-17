package com.abc.component.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitMQConfig {

    @Bean
    public Queue CodeDirectQueue(){
        return new Queue("CodeDirectQueue",true);
    }

    @Bean
    public DirectExchange CodeDirectExchange(){
        return new DirectExchange("CodeDirectExchange");
    }

    @Bean
    public Binding BindingDirect(){
        return BindingBuilder.bind(CodeDirectQueue()).to(CodeDirectExchange()).with("CodeDirectRouting");
    }

}
