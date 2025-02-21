package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.global.common.DtoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatConnector chatConnector;

    @Transactional
    public ChatResponse addForAllRoomToMessage(ChatRequest chatsRequest, Long userId) {
        Chat chats = chatsRequest.toEntity(userId);
        chatConnector.save(chats);

        return DtoFactory.toChatDto(chats);
    }

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsHistory() {
        List<Chat> chats = chatConnector.findAll();
        return chats.stream().map(DtoFactory::toChatDto).toList();
    }

    @Transactional
    public ChatResponse addForRoomToMessage(ChatRequest chatsRequest, String roomId) {
        return null;
    }
}
