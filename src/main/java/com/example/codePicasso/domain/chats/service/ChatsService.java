package com.example.codePicasso.domain.chats.service;

import com.example.codePicasso.domain.chats.dto.request.ChatsRequest;
import com.example.codePicasso.domain.chats.dto.response.ChatsResponse;
import com.example.codePicasso.domain.chats.entity.Chats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatsService {

    private final ChatsConnector chatsConnector;

    @Transactional
    public ChatsResponse addMessage(ChatsRequest chatsRequest, Long userId) {
        Chats chats = chatsRequest.toEntity(userId);
        chatsConnector.save(chats);
        return chats.toDto();
    }

    public List<ChatsResponse> getChatsHistory() {
        List<Chats> chats = chatsConnector.findAll();
        return chats.stream().map(Chats::toDto).toList();
    }
}
