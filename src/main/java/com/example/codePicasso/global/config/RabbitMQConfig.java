package com.example.codePicasso.global.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
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
    public Queue anonymousQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding bindingReceiveQueue(FanoutExchange rabbitExchange, Queue anonymousQueue) {
        return BindingBuilder.bind(anonymousQueue).to(rabbitExchange);
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
