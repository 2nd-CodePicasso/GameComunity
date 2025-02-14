package com.example.codePicasso.domain.chat.controller;

import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.service.ChatService;
import com.example.codePicasso.global.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatsService;
    private final JwtUtil jwtUtil;

    @MessageMapping("/send")
    @SendTo("/topic/hi")
    public ChatResponse sendMessage(Message<ChatRequest> message) {
        log.info(Thread.currentThread().getName());
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String authorization = accessor.getFirstNativeHeader("Authorization");
        String user = jwtUtil.getString(authorization);
        Long userId = Long.valueOf(user);
        return chatsService.addMessage(message.getPayload(), userId);
    }


    @MessageMapping("/test")
    @SendTo("/topic/ih")
    public ChatRequest testMessage(@Payload ChatRequest chatsRequest) {
        log.info("33");
        return chatsRequest;
    }

}
