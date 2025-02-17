package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.service.ChatConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatConnectorImpl implements ChatConnector {
    private final ChatRepository chatsRepository;


    @Override
    public Chat save(Chat chats) {
        return chatsRepository.save(chats);
    }

    @Override
    public List<Chat> findAll() {
        return chatsRepository.findAll();
    }

    @Override
    public List<Chat> findAllByRoomId(Long roomId) {
        return chatsRepository.findAllByChatRoomId(roomId);
    }
}
