package com.example.codePicasso.domain.chats.repository;

import com.example.codePicasso.domain.chats.entity.Chats;
import com.example.codePicasso.domain.chats.service.ChatsConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatsConnectorImpl implements ChatsConnector {
    private final ChatsRepository chatsRepository;


    @Override
    public Chats save(Chats chats) {
        return chatsRepository.save(chats);
    }

    @Override
    public List<Chats> findAll() {
        return chatsRepository.findAll();
    }
}
