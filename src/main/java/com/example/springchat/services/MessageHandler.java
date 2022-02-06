package com.example.springchat.services;

import com.example.springchat.entities.ChatDto;

public interface MessageHandler {

	void handleIncomingMessage(ChatDto msg);

}
