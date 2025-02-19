package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.service.RoomConnector;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomConnectorImpl implements RoomConnector {
    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return null;
    }

    @Override
    public List<ChatRoom> findAll() {
        return List.of();
    }

    @Override
    public ChatRoom findByName(String roomName) {
        return null;
    }

    @Override
    public ChatRoom findByIdAndUserId(Long aLong, Long userId) {
        return null;
    }
}
