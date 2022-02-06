package com.example.springchat.configs;

import com.example.springchat.entities.AppEnv;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class RabbitMQConfig implements RabbitListenerConfigurer {

    @Autowired
    @Bean
    Queue queue(AppEnv settings) {
        return new Queue(settings.getRabbit().getQueue() + ".25", false);
    }

    @Autowired
    @Bean
    Queue queue1(AppEnv settings) {
        return new Queue(settings.getRabbit().getQueue() + ".38", false);
    }

    // @Autowired
    // @Bean
    // TopicExchange exchange(AppEnv settings) {
    //     return new TopicExchange(settings.getRabbit().getExchange());
    // }

    @Autowired
    @Bean
    FanoutExchange exchange(AppEnv settings) {
        return new FanoutExchange(settings.getRabbit().getExchange());
    }

    @Bean
    Binding bindingExchange(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
                // .with("queue.chat.#");
    }

    @Bean
    Binding bindingExchange1(Queue queue1, FanoutExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange);
                // .with("queue.chat.#");
    }

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }


    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(jackson2Converter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

}
