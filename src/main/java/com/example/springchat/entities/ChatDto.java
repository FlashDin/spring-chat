package com.example.springchat.entities;

import lombok.Data;

@Data
public class ChatDto {

    private Long id;
    private String me;
    private String you;
    private String message;

    public ChatDto() {

    }

    public ChatDto(Chat entity) {
        if (entity.getId() != null) {
            this.id = entity.getId();
        }
        this.me = entity.getMe();
        this.you = entity.getYou();
        this.message = entity.getMessage();
    }


}
