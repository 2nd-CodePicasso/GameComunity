package com.example.codePicasso.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.stereotype.Component;

@Component
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketTime {

    private final WebSocketMessageBrokerStats stats;

    @Scheduled(fixedRate =10000)  // 60초마다 로그 출력
    public void logStats() {
        System.out.println("WebSocket Stats: " + stats.toString());
    }
}