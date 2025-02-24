package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.domain.chat.service.GlobalChatConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GlobalChatConnectorImpl implements GlobalChatConnector {

    private final GlobalChatRepository globalChatRepository;

    @Override
    public GlobalChat save(GlobalChat chats) {
        return globalChatRepository.save(chats);
    }

    @Override
    public List<GlobalChat> findAll() {
        return globalChatRepository.findAll();
    }
}
