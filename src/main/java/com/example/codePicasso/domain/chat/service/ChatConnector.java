package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.entity.Chat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChatConnector {
    Chat save(Chat chats);

    List<Chat> findAll();

    List<Chat> findAllByRoomId(Long roomId);

    Chat findById(Long aLong);
}
