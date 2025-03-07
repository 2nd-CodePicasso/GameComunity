package com.example.codePicasso.global.config;

import com.example.codePicasso.global.common.CustomUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel messageChannel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        List<String> authHeaders = accessor.getNativeHeader("Authorization");
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String token = authHeaders.get(0).replace("Bearer ", "");
            try {

                Claims claims = jwtUtil.extractClaims(token);

                String userId = claims.getSubject();
                String username = claims.get("username", String.class);

                accessor.setNativeHeader("userId",userId);
                accessor.setNativeHeader("username",username);

                return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
            } catch (Exception e) {
                log.warn("WebSocket JWT 검증 실패", e);

            }
        }
        return message;
    }

}
