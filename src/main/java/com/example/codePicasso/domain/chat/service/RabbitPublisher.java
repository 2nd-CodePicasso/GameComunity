//package com.example.codePicasso.domain.chat.service;
//
//import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
//import com.example.codePicasso.domain.chat.dto.response.GlobalChatDto;
//import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
//import com.example.codePicasso.domain.chat.entity.GlobalChat;
//import com.example.codePicasso.global.common.DtoFactory;
//import com.vdurmont.emoji.EmojiParser;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class RabbitPublisher {
//    private final RabbitTemplate rabbitTemplate;
//
//    public void publishMessage(ChatRequest chatRequest, Long userId, String username) {
//        String emoji = EmojiParser.parseToUnicode(chatRequest.message());
//        GlobalChat globalChat = chatRequest.toEntityFromGlobalChat(userId,emoji,username);
//        GlobalChatDto rabbitDto = DtoFactory.toRabbitDto(globalChat);
//        rabbitTemplate.convertAndSend("rabbit","",rabbitDto);
//    }
//
//}
