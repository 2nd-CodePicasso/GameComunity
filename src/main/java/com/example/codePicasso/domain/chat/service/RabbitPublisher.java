package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.global.common.DtoFactory;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitPublisher {

    private final GlobalChatConnector globalChatConnector;
    private final RabbitTemplate rabbitTemplate;
    private final RoomConnector roomConnector;

    public void publishMessage(ChatRequest chatRequest, Long userId, String username) {
        String emoji = EmojiParser.parseToUnicode(chatRequest.message());
        GlobalChat globalChat = chatRequest.toEntityFromGlobalChat(userId,emoji,username);
        GlobalChat chats = globalChatConnector.save(globalChat);
        GlobalChatResponse globalChatDto = DtoFactory.toGlobalChatDto(chats);
        rabbitTemplate.convertAndSend("rabbit","",globalChatDto);
    }

    public void addForRoomToMessage(ChatRequest chatsRequest, Long roomId, Long userId, String username) {
        String emoji = EmojiParser.parseToUnicode(chatsRequest.message());
        ChatRoom chatRoom = roomConnector.findById(roomId);
        Chat chat = chatsRequest.toEntityFromChat(userId, chatRoom, emoji, username);
        ChatResponse chatDto = DtoFactory.toChatDto(chat);
        rabbitTemplate.convertAndSend("roombit","room."+roomId,chatDto);
    }
}
