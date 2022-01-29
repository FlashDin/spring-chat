package com.example.springchat.controllers;

import com.example.springchat.entities.ChatDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatControllerTest {

    @Autowired
    private ChatController chatController;

    private ChatDto chatDto;

    @BeforeEach
    void setUp() {
        chatDto = new ChatDto();
        chatDto.setMe("found");
        chatDto.setYou("yout");
        chatDto.setMessage("Kamu hehe banget");
    }

    @Test
    void findByUser() {
        assertTrue(chatController.findByUser("nfound", null, Pageable.ofSize(10)).isEmpty());
        assertFalse(chatController.findByUser("found", null, Pageable.ofSize(10)).isEmpty());
    }

    @Test
    void save() {
        assertTrue(Optional.of(chatController.save(chatDto)).isPresent());
    }

    @Test
    void update() {
        chatDto.setId(Long.valueOf(1));
        chatDto.setYou("komodo");
        assertTrue(Optional.of(chatController.update(chatDto)).isPresent());
    }

    @Test
    void delete() {
        assertTrue(Optional.of(chatController.delete(Long.valueOf(1))).isPresent());
    }
}