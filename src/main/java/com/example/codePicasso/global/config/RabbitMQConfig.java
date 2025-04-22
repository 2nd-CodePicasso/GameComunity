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
    public TopicExchange topicExchange() {
        return new TopicExchange("roombit");
    }

    @Bean
    public Queue fanOutAnonymousQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue topicAnonymousQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding topicBinding(Queue topicAnonymousQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicAnonymousQueue).to(topicExchange).with("room.*");
    }

    @Bean
    public Binding fanOutQueue(FanoutExchange rabbitExchange, Queue fanOutAnonymousQueue) {
        return BindingBuilder.bind(fanOutAnonymousQueue).to(rabbitExchange);
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
