package com.example.codePicasso.domain.chat.controller;

import com.example.codePicasso.domain.chat.dto.request.SecurityChatRequest;
import com.example.codePicasso.domain.chat.dto.response.ChatListResponse;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatListResponse;
import com.example.codePicasso.domain.chat.service.ChatService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    public ResponseEntity<ApiResponse<GlobalChatListResponse>> getChatsHistory(
            @RequestParam Long chatId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastTime,
            @RequestParam int size
    ) {
        GlobalChatListResponse chatsHistory = chatsService.getChatsHistory(chatId, lastTime, size);

        return ApiResponse.success(chatsHistory);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<ChatListResponse>> getChatsByRoomId(
            @PathVariable Long roomId,
            @RequestParam int size,
            @RequestParam Long chatId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastTime
    ) {
        if (lastTime == null) {
            lastTime = LocalDateTime.now();
        }
        ChatListResponse chatListResponse = chatsService.getByRoomId(roomId, size, chatId, lastTime);

        return ApiResponse.success(chatListResponse);
    }
}
