//package com.example.save_chat.rabbit;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RequiredArgsConstructor
//public class RabbitMessageListener {
//
//    private final GlobalChatConnector globalChatConnector;
//
//
//    @RabbitListener(queues = "rabbit.save")
//    public void saveMessage(GlobalChatDto globalChatDto) {
//        GlobalChat globalChat = globalChatDto.toEntity();
//        globalChatConnector.save(globalChat);
//    }
//}
