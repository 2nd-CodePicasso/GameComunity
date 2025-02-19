package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.domain.chat.service.GlobalChatConnector;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GlobalChatConnectorImpl implements GlobalChatConnector {
    @Override
    public GlobalChat save(GlobalChat chats) {
        return null;
    }

    @Override
    public List<GlobalChat> findAll() {
        return List.of();
    }
}
