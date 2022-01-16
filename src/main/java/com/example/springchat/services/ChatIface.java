package com.example.springchat.services;

import com.example.springchat.entities.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatIface extends BaseCrud {

    Page<Chat> findByUser(String me, String you, Pageable pageable);

}
