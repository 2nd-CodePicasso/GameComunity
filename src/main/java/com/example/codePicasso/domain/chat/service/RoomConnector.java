package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.entity.ChatRoom;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoomConnector {
    ChatRoom save(ChatRoom chatRoom);

    List<ChatRoom> findAll();

    ChatRoom findByName(String roomName);

    ChatRoom findByIdAndUserId(Long roomId, Long userId);

    void deleteRoom(ChatRoom chatRoom);

    ChatRoom findById(Long roomId);
}
