package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.global.exception.JacksonFailException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageBody = new String(message.getBody());

        try {
            GlobalChatResponse globalChatResponse = objectMapper.readValue(messageBody, GlobalChatResponse.class);
            simpMessagingTemplate.convertAndSend("/topic/hi", globalChatResponse);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new JacksonFailException(ErrorCode.REDIS_JACKSON_EXCEPTION);
        }
    }
}
