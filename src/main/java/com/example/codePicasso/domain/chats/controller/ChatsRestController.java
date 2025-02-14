package com.example.codePicasso.domain.chats.controller;

import com.example.codePicasso.domain.chats.dto.response.ChatsListResponse;
import com.example.codePicasso.domain.chats.dto.response.ChatsResponse;
import com.example.codePicasso.domain.chats.service.ChatsService;
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
public class ChatsRestController {

    private final ChatsService chatsService;

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<ChatsListResponse>> getChatsHistory() {
        List<ChatsResponse> chatsHistory = chatsService.getChatsHistory();
        return ApiResponse.success(ChatsListResponse.builder().chatsResponseList(chatsHistory).build());
    }
}
