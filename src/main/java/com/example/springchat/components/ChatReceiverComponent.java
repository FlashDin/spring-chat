package com.example.springchat.components;

import com.example.springchat.entities.Chat;
import com.example.springchat.entities.ChatDto;
import com.example.springchat.repositories.ChatRepository;
import com.example.springchat.services.MessageHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class ChatReceiverComponent {

    @Autowired
    @Getter
    @Setter
    private ChatRepository chatRepository;

    @Autowired
    private ChatSenderComponent senderService;

    private final AtomicInteger counter = new AtomicInteger(0);

    @Autowired(required = false)
    private Collection<MessageHandler> messageHandlers;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    // @RabbitListener(queues = "#{appEnv.rabbit.queue}")
    // public void handleMessage(ChatDto dto) {
    //     // Chat chat = new Chat(dto);
    //     // dto = Optional.of(this.chatRepository.save(chat)).map(ChatDto::new).orElse(null);
    //     // System.out.println(dto);
    //     final int ordinal = counter.incrementAndGet();
    //     log.debug("Message {} received: {}", ordinal, dto.getMessage());
    //     if (messageHandlers != null) {
    //         messageHandlers.forEach(h -> h.handleIncomingMessage(dto));
    //     }
    // }

}
