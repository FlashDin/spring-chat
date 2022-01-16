package com.example.springchat.controllers;

import com.example.springchat.entities.Chat;
import com.example.springchat.entities.ChatDto;
import com.example.springchat.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<Page<ChatDto>> findByUser(@RequestParam String me,
                                                    @RequestParam String you,
                                                    @PageableDefault Pageable pageable) {
        Page<ChatDto> dtos = chatService.findByUser(me, you, pageable)
                .map(ChatDto::new);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ChatDto> save(@RequestBody ChatDto dto) {
        Chat chat = new Chat(dto);
        dto = Optional.of(chatService.getChatRepository().save(chat)).map(ChatDto::new).orElse(null);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<ChatDto> update(@RequestBody ChatDto dto) {
        return this.save(dto);
    }

    @DeleteMapping
    public ResponseEntity<ChatDto> delete(Long id) {
        Optional<Chat> chat = chatService.getChatRepository().findById(id);
        ChatDto dto = chat.map(ChatDto::new).orElse(null);
        if (chat.isPresent()) {
            try {
                chatService.getChatRepository().deleteById(id);
            } catch (Exception e) {
                return ResponseEntity.ok(null);
            }
        }
        return ResponseEntity.ok(dto);
    }

}
