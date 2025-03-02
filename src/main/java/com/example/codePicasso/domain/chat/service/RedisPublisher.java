package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.global.exception.JacksonFailException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisPublisher {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;
    private final GlobalChatConnector globalChatConnector;

    public void publishMessage(ChatRequest chatRequest, Long userId, String username) {
        String parsedMessage = EmojiParser.parseToUnicode(chatRequest.message());
        GlobalChat globalChat = chatRequest.toEntityFromGlobalChat(userId, parsedMessage, username);
        GlobalChatResponse globalChatDto = DtoFactory.toGlobalChatDto(globalChat);
        try {
            String messageInfo = objectMapper.writeValueAsString(globalChatDto);
            stringRedisTemplate.convertAndSend("redis", messageInfo);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new JacksonFailException(ErrorCode.REDIS_JACKSON_EXCEPTION);
        }
        globalChatConnector.save(globalChat);
    }
}
