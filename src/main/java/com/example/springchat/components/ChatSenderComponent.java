package com.example.springchat.components;

import com.example.springchat.entities.AppEnv;
import com.example.springchat.entities.ChatDto;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class ChatSenderComponent {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    @Autowired
    private MappingJackson2MessageConverter mappingJackson2MessageConverter;

    @Autowired
    private AppEnv settings;

    public void sender(ChatDto dto) {
        this.rabbitMessagingTemplate.setMessageConverter(this.mappingJackson2MessageConverter);
        this.rabbitMessagingTemplate.convertAndSend(this.settings.getRabbit().getExchange(), "", dto);
    }

}
