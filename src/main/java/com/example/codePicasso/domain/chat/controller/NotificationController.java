package com.example.codePicasso.domain.chat.controller;

import com.example.codePicasso.domain.chat.dto.response.NotificationListResponse;
import com.example.codePicasso.domain.chat.dto.response.NotificationResponse;
import com.example.codePicasso.domain.chat.service.NotificationService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotification(
            @PathVariable Long roomId
    ) {
        NotificationResponse lastNotification = notificationService.findLastNotification(roomId);

        return ApiResponse.success(lastNotification);
    }

    @GetMapping("/{roomId}/all")
    public ResponseEntity<ApiResponse<NotificationListResponse>> getAllNotification(
            @PathVariable Long roomId
    ) {
        NotificationListResponse allNotification = notificationService.findAllNotification(roomId);

        return ApiResponse.success(allNotification);
    }
}
