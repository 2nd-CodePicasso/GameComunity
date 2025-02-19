package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.entity.Chat;
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

        return chats.toDto();
    }

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsHistory() {
        List<Chat> chats = chatConnector.findAll();
        return chats.stream().map(Chat::toDto).toList();
    }

    @Transactional
    public ChatResponse addForRoomToMessage(ChatRequest chatsRequest, String roomId) {
        return null;
    }
}
