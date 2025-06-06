package com.example.codePicasso.domain.chat.controller;

    import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
    import com.example.codePicasso.domain.chat.dto.request.NotificationRequest;
    import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
    import com.example.codePicasso.domain.chat.dto.response.NotificationResponse;
    import com.example.codePicasso.domain.chat.service.ChatService;
    import com.example.codePicasso.domain.chat.service.NotificationService;
    import com.example.codePicasso.domain.chat.service.RabbitPublisher;
    import com.example.codePicasso.domain.chat.service.RedisPublisher;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.messaging.handler.annotation.*;
    import org.springframework.stereotype.Controller;

    @Slf4j
    @Controller
    @RequiredArgsConstructor
    public class ChatController {

        private final ChatService chatService;
        private final NotificationService notificationService;
        private final RedisPublisher redisPublisher;
        private final RabbitPublisher rabbitPublisher;

        @MessageMapping("/send/all")
        public void sendMessage(
                @Payload ChatRequest chatRequest,
                @Header("userId") String userId,
                @Header("username") String username
        ) {
            //심플 메시지브로커+RDB
          // chatService.addForAllRoomToMessage(chatRequest, Long.valueOf(userId),username);

            //레디스+RDB
          //  redisPublisher.publishMessage(chatRequest,Long.valueOf(userId),username);

            //레빗MQ
           rabbitPublisher.publishMessage(chatRequest, Long.valueOf(userId), username);
        }

        @MessageMapping("/send/room/{roomId}")
        public ChatResponse testMessage(
                @Payload ChatRequest chatsRequest,
                @DestinationVariable Long roomId,
                @Header("userId") String userId,
                @Header("username") String username
        ) {
            return chatService.addForRoomToMessage(chatsRequest, roomId, Long.valueOf(userId),username);
        }

        @MessageMapping("/send/room/notification")
        @SendTo("/topic/{roomId}")
        public NotificationResponse sendNotification(
                @Payload NotificationRequest notificationRequest,
                @Header("userId") String userId
        ) {
            return notificationService.addNotification(notificationRequest, Long.valueOf(userId));
        }


    }
