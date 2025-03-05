package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface GlobalChatConnector {
    GlobalChat save(GlobalChat chats);

    List<GlobalChat> findAll(Long chatId, LocalDateTime lastTime, int size);

    void saveAll(List<GlobalChatResponse> messages);
}
