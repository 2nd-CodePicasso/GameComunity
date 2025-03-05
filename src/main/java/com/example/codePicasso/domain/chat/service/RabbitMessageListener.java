package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RabbitMessageListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = "rabbit",ackMode = "MANUAL")
    public void receiveMessage(GlobalChatResponse globalChatResponse) {
        simpMessagingTemplate.convertAndSend("/topic/hi", globalChatResponse);
    }
}
