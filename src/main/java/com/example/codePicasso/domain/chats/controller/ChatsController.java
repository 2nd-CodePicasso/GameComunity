package com.example.codePicasso.domain.chats.controller;

import com.example.codePicasso.domain.chats.dto.request.ChatsRequest;
import com.example.codePicasso.domain.chats.dto.response.ChatsResponse;
import com.example.codePicasso.domain.chats.service.ChatsService;
import com.example.codePicasso.global.config.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatsController {

    private final ChatsService chatsService;
    private final JwtUtil jwtUtil;

    @MessageMapping("/send")
    @SendTo("/topic/hi")
    public ChatsResponse sendMessage(Message<ChatsRequest> message) {
        log.info(Thread.currentThread().getName());
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String authorization = accessor.getFirstNativeHeader("Authorization");
        String user = jwtUtil.getString(authorization);
        //나는 무지렁이다...
        Long userId = Long.valueOf(user);
        return chatsService.addMessage(message.getPayload(), userId);
    }


    @MessageMapping("/test")
    @SendTo("/topic/ih")
    public ChatsRequest testMessage(@Payload ChatsRequest chatsRequest) {
        log.info("33");
        return chatsRequest;
    }

}
