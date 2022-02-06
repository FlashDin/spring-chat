package com.example.springchat.services;

import com.example.springchat.entities.Chat;
import com.example.springchat.repositories.ChatRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService implements ChatIface {

    @Autowired
    @Getter
    @Setter
    private ChatRepository chatRepository;

    @Override
    public Page<Chat> findByUser(String me, String you, Pageable pageable) {
        List<Chat> chatList = this.chatRepository.findAll(pageable)
                .filter(chat1 -> (me.equals(chat1.getMe()) && you.equals(chat1.getYou()))
                        || (you.equals(chat1.getMe()) && me.equals(chat1.getYou())))
                .toList();
        return new PageImpl(chatList,
                pageable, chatList.size());
    }

}
