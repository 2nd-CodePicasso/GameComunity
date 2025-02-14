package com.example.codePicasso.domain.chats.service;

import com.example.codePicasso.domain.chats.entity.Chats;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChatsConnector {
    Chats save(Chats chats);

    List<Chats> findAll();
}
