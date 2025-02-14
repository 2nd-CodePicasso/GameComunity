package com.example.codePicasso.domain.chats.service;

import com.example.codePicasso.domain.chats.dto.request.ChatsRequest;
import com.example.codePicasso.domain.chats.dto.response.ChatsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatsService {

    private final ChatsConnector chatsConnector;

    public ChatsResponse addMessage(ChatsRequest chatsRequest) {
        return null;
    }
}
