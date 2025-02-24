package com.example.codePicasso.domain.chat.controller;

import com.example.codePicasso.domain.chat.dto.response.ChatListResponse;
import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.service.ChatService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatsService;

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<ChatListResponse>> getChatsHistory() {
        ChatListResponse chatsHistory = chatsService.getChatsHistory();
        return ApiResponse.success(chatsHistory);
    }
}
