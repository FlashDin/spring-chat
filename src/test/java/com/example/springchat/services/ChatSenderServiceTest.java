package com.example.springchat.services;

import com.example.springchat.components.ChatSenderComponent;
import com.example.springchat.entities.ChatDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatSenderServiceTest {

    @Autowired
    private ChatSenderComponent senderService;

    private ChatDto chatDto;

    @BeforeEach
    void setUp() {
        chatDto = new ChatDto();
        chatDto.setMe("found");
        chatDto.setYou("yout");
        chatDto.setMessage("Kamu hehe banget");
    }

    @Test
    void sender() {
        senderService.sender(chatDto);
    }
}