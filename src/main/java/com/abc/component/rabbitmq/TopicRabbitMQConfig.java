package com.abc.component.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class TopicRabbitMQConfig {

    @Bean
    public Queue updateQueue(){
        return new Queue("topic.update");
    }

    @Bean
    public Queue addQueue(){
        return new Queue("topic.add");
    }

    @Bean
    public Queue removeQueue(){
        return new Queue("topic.remove");
    }

    @Bean
    TopicExchange addExchange(){
        return new TopicExchange("addExchange");
    }

    @Bean
    TopicExchange removeExchange(){
        return new TopicExchange("removeExchange");
    }

    @Bean
    Binding BindingExchange(){
        return BindingBuilder.bind(updateQueue()).to(addExchange()).with("topic.update");
    }

    @Bean
    Binding BindingExchange2(){
        return BindingBuilder.bind(addQueue()).to(addExchange()).with("topic.#");
    }

    @Bean
    Binding BindingExchange3(){
        return BindingBuilder.bind(updateQueue()).to(removeExchange()).with("topic.update");
    }

    @Bean
    Binding BindingExchange4(){
        return BindingBuilder.bind(removeQueue()).to(removeExchange()).with("topic.#");
    }
}
