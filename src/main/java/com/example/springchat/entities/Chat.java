package com.example.springchat.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String me;
    @Column
    private String you;
    @Column
    private String message;

    public Chat() {
    }

    public Chat(ChatDto dto) {
        if (dto.getId() != null) {
            this.id = dto.getId();
        }
        this.me = dto.getMe();
        this.you = dto.getYou();
        this.message = dto.getMessage();
    }
}
