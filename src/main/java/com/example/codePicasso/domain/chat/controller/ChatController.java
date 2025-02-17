    package com.example.codePicasso.domain.chat.controller;

    import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
    import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
    import com.example.codePicasso.domain.chat.service.ChatService;
    import com.example.codePicasso.global.config.JwtUtil;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.messaging.Message;
    import org.springframework.messaging.handler.annotation.Header;
    import org.springframework.messaging.handler.annotation.MessageMapping;
    import org.springframework.messaging.handler.annotation.Payload;
    import org.springframework.messaging.handler.annotation.SendTo;
    import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
    import org.springframework.messaging.simp.user.SimpUser;
    import org.springframework.messaging.simp.user.SimpUserRegistry;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.socket.WebSocketSession;

    import java.util.Map;
    import java.util.Set;

    @Slf4j
    @Controller
    @RequiredArgsConstructor
    public class ChatController {

        private final ChatService chatsService;
        private final JwtUtil jwtUtil;
        private final SimpUserRegistry simpUserRegistry;

        @MessageMapping("/send")
        @SendTo("/topic/hi")
        public ChatResponse sendMessage(
                ChatRequest chatRequest,
                @Header("userId") String userId
        ) {
            return chatsService.addMessage(chatRequest, Long.valueOf(userId));
        }


        @MessageMapping("/test")
        @SendTo("/topic/ih")
        public ChatRequest testMessage(@Payload ChatRequest chatsRequest) {
            log.info("33");
            return chatsRequest;
        }

    }
