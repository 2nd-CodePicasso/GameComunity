package com.example.codePicasso.global;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebsocketTest {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final String WS_URL = "ws://localhost:8080/ws";

        @Test
        public void testWebSocketHandshake() throws Exception {
            var client = new StandardWebSocketClient();
            var stompClient = new WebSocketStompClient(client);
            var handshakeFuture = new CompletableFuture<Boolean>();

            StompSessionHandlerAdapter sessionHandler = new StompSessionHandlerAdapter() {
                @Override
                public void afterConnected(StompSession session, StompHeaders c) {
                    System.out.println("핸드세이크성공");
                    handshakeFuture.complete(true);
                }

                @Override
                public void handleTransportError(StompSession session, Throwable exception) {
                    System.out.println("핸드세이크실패");
                    handshakeFuture.complete(false);
                }
            };

            stompClient.connectAsync(WS_URL, sessionHandler);

            boolean handshakeSuccess = handshakeFuture.get(3, TimeUnit.SECONDS);
            assertThat(handshakeSuccess).isTrue();
        }
    }

