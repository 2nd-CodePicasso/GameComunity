package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.response.GlobalChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class RabbitMessageListener {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GlobalChatConnector globalChatConnector;
    private static final int BATCH_SIZE = 1000;
    List<GlobalChatDto> messages = new ArrayList<>();

    @RabbitListener(queues = "rabbit.receive")
    public void receiveMessage(GlobalChatDto globalChatDto) {
        simpMessagingTemplate.convertAndSend("/topic/hi", globalChatDto);

    }

    @RabbitListener(queues = "rabbit.save")
    public void saveMessage(GlobalChatDto globalChatDto) {
        messages.add(globalChatDto);
        if (messages.size() >= BATCH_SIZE) {
            globalChatConnector.saveAll(messages);
            messages.clear();
        }
    }
}
