package com.example.codePicasso.global.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel messageChannel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        List<String> authHeaders = accessor.getNativeHeader("Authorization");
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String token = authHeaders.get(0).replace("Bearer ", "");
            try {

                Claims claims = jwtUtil.extractClaims(token);

                String username = claims.getSubject();
                String roles = claims.get("roles", String.class);

                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(roles));

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null,authorities);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                accessor.setHeader("userId", username); // 커스텀 헤더 추가

                log.info("WebSocket 메시지 인증 성공: {}", username);
                log.info(Thread.currentThread().getName(),Thread.currentThread().getId());
            } catch (Exception e) {
                log.warn("WebSocket JWT 검증 실패", e);
            }
        }
        return message;
    }

}
