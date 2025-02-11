package com.example.codePicasso.domain.chats.controller;

import com.example.codePicasso.domain.chats.dto.request.ChatsRequest;
import com.example.codePicasso.domain.chats.dto.response.ChatsResponse;
import com.example.codePicasso.domain.chats.service.ChatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatsController {

    private final ChatsService chatsService;


    @MessageMapping
    @SendTo
    public ChatsResponse sendMessage(ChatsRequest chatsRequest, Principal principal) {
        return chatsService.addMessage(chatsRequest);
    }
}
