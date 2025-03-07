//package com.example.codePicasso.domain.chat.service;
//
//import com.example.codePicasso.domain.chat.dto.response.GlobalChatDto;
//import com.example.codePicasso.domain.chat.entity.GlobalChat;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//
//
//@Component
//@RequiredArgsConstructor
//public class RabbitMessageListener {
//
//    private final SimpMessagingTemplate simpMessagingTemplate;
//    private final GlobalChatConnector globalChatConnector;
//
//    @RabbitListener(queues = "rabbit.receive")
//    public void receiveMessage(GlobalChatDto globalChatDto) {
//        simpMessagingTemplate.convertAndSend("/topic/hi", globalChatDto);
//    }
//
////    @RabbitListener(queues = "rabbit.save")
////    public void saveMessage(GlobalChatDto globalChatDto) {
////        GlobalChat globalChat = globalChatDto.toEntity();
////        globalChatConnector.save(globalChat);
////    }
//}
