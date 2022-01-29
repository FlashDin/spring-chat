package com.example.springchat.controllers;

import com.example.springchat.entities.Chat;
import com.example.springchat.entities.ChatDto;
import com.example.springchat.services.ChatService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @SneakyThrows
    @MessageMapping("/chat/find")
    public Page<ChatDto> findByUser(String me,
                                                    String you,
                                                    @PageableDefault Pageable pageable) {
        Page<ChatDto> dtos = chatService.findByUser(me, you, pageable)
                .map(ChatDto::new);
        Thread.sleep(1000);
        return dtos;
    }

    @MessageMapping("/chat/save")
    public ChatDto save(ChatDto dto) {
        Chat chat = new Chat(dto);
        dto = Optional.of(chatService.getChatRepository().save(chat)).map(ChatDto::new).orElse(null);
        return dto;
    }

    @MessageMapping("/chat/save-get")
    @SendTo("/topic/chat")
    public Page<ChatDto> saveGet(ChatDto dto,
                                                 @Header int page,
                                                 @Header int size,
                                                 @Header String sort,
                                                 @Header String direction) {
        dto = this.save(dto);
        if (dto == null) {
            return null;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        return this.findByUser(dto.getMe(), dto.getYou(), pageable);
    }

    @MessageMapping("/chat/update")
    public ChatDto update(ChatDto dto) {
        return this.save(dto);
    }

    @MessageMapping("/chat/delete")
    public ChatDto delete(Long id) {
        Optional<Chat> chat = chatService.getChatRepository().findById(id);
        ChatDto dto = chat.map(ChatDto::new).orElse(null);
        if (chat.isPresent()) {
            try {
                chatService.getChatRepository().deleteById(id);
            } catch (Exception e) {
                return null;
            }
        }
        return dto;
    }

}
