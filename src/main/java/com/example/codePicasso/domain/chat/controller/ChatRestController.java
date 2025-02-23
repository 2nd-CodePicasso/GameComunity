package com.example.codePicasso.domain.chat.controller;

import com.example.codePicasso.domain.chat.dto.request.SecurityChatRequest;
import com.example.codePicasso.domain.chat.dto.response.ChatListResponse;
import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatListResponse;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.domain.chat.service.ChatService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatsService;

    @PostMapping
    public ResponseEntity<ApiResponse<ChatListResponse>> getSecurityChatsHistory(
            @RequestBody SecurityChatRequest securityChatRequest
    ) {
        ChatListResponse securityChatsHistory = chatsService.getSecurityChatsHistory(securityChatRequest);
        return ApiResponse.success(securityChatsHistory);
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<GlobalChatListResponse>> getChatsHistory() {
        GlobalChatListResponse chatsHistory = chatsService.getChatsHistory();
        return ApiResponse.success(chatsHistory);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<ChatListResponse>> getChatsByRoomId(
            @PathVariable Long roomId
    ) {
        ChatListResponse chatListResponse = chatsService.getByRoomId(roomId);
        return ApiResponse.success(chatListResponse);
    }

}
