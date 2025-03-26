package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.response.GlobalChatDto;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.global.common.DtoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RabbitMessageListener {

    private final GlobalChatConnector globalChatConnector;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @RabbitListener(queues = "#{anonymousQueue.name}")
    public void receiveMessage(GlobalChatDto globalChatDto) {
        simpMessagingTemplate.convertAndSend("/topic/hi", globalChatDto);
    }
}
