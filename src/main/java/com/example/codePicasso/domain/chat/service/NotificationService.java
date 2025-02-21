package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.controller.NotificationListResponse;
import com.example.codePicasso.domain.chat.dto.request.NotificationRequest;
import com.example.codePicasso.domain.chat.dto.response.NotificationResponse;
import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.Notification;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.common.DtoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationConnector notificationConnector;
    private final UserConnector userConnector;
    private final ChatConnector chatConnector;

    @Transactional
    public NotificationResponse addNotification(NotificationRequest notificationRequest, Long userId) {
        User user = userConnector.findById(userId);
        Chat chat = chatConnector.findById(notificationRequest.messageId());
        Notification notification = notificationRequest.toEntity(user, chat,chat.getChatRoom());
        Notification saveNotification = notificationConnector.save(notification);

        return DtoFactory.toNotificationDto(saveNotification);
    }

    public NotificationResponse findLastNotification(Long roomId) {
        Notification notification = notificationConnector.findLastByRoomId(roomId);
        return DtoFactory.toNotificationDto(notification);
    }

    public NotificationListResponse findAllNotification(Long roomId) {
        List<Notification> notifications = notificationConnector.findAllByRoomId(roomId);
        return NotificationListResponse.builder()
                .notificationResponses(notifications.stream().map(DtoFactory::toNotificationDto).toList())
                .build();
    }
}
