package com.example.springchat.console;

import java.util.Scanner;

import com.example.springchat.entities.ChatDto;
import com.example.springchat.components.ChatSenderComponent;
import com.example.springchat.services.MessageHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("cli")
public class ConsoleRunner implements CommandLineRunner, MessageHandler {

	private final ChatSenderComponent publisher;

	@Autowired
	public ConsoleRunner(ChatSenderComponent publisher) {
		this.publisher = publisher;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("### RabbitMQ Chat ###");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("> ");
			String line = scanner.nextLine();
			ChatDto dto = new ChatDto();
			dto.setMe("me");
			dto.setYou("you");
			dto.setMessage(line);
			publisher.sender(dto);
		}
	}

	@Override
	public void handleIncomingMessage(@NonNull ChatDto msg) {
		System.out.println(msg.getMessage());
	}

}
