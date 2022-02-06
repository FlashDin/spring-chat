package com.example.springchat.configs;

import com.example.springchat.entities.AppEnv;
import com.example.springchat.entities.Rabbit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

// @Configuration
// @EnableRabbit
public class RabbitConfig {

    @Bean
    @Autowired
    public ConnectionFactory connectionFactory(AppEnv settings, @TaskExecutorConfig.RabbitConnectionExecutor TaskExecutor executor) {
        Rabbit rabbitSettings = settings.getRabbit();
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitSettings.getHost(), rabbitSettings.getPort());
        connectionFactory.setUsername(rabbitSettings.getUser());
        connectionFactory.setPassword(rabbitSettings.getPass());
        connectionFactory.setExecutor(executor);
        return connectionFactory;
    }

    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @Autowired
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(AppEnv settings,
                                                                         ConnectionFactory connectionFactory,
                                                                         MessageConverter messageConverter,
                                                                         @TaskExecutorConfig.RabbitListenerExecutor TaskExecutor executor) {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(settings.getRabbit().getMaxConcurrentConsumers());
        factory.setMaxConcurrentConsumers(settings.getRabbit().getMaxConcurrentConsumers());
        factory.setMessageConverter(messageConverter);
        factory.setTaskExecutor(executor);
        return factory;
    }

    @Bean
    @Autowired
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        final Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(objectMapper);
        return messageConverter;
    }

    // @Bean
    // public MappingJackson2MessageConverter jackson2Converter() {
    //     MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    //     return converter;
    // }

}
