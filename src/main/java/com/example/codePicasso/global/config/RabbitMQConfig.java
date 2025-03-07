package com.example.codePicasso.global.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange rabbitExchange() {
        return new FanoutExchange("rabbit");
    }

    @Bean
    public Queue rabbitSaveQueue() {
        return new Queue("rabbit.save", true);
    }

    @Bean
    public Queue rabbitReceiveQueue() {
        return new Queue("rabbit.receive", true);
    }

    @Bean
    public Binding bindingReceiveQueue(FanoutExchange rabbitExchange, Queue rabbitReceiveQueue) {
        return BindingBuilder.bind(rabbitReceiveQueue).to(rabbitExchange);
    }

    @Bean
    public Binding bindingSaveQueue(FanoutExchange rabbitExchange, Queue rabbitSaveQueue) {
        return BindingBuilder.bind(rabbitSaveQueue).to(rabbitExchange);
    }


    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost("localhost");
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
