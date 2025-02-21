package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.NotificationRequest;
import com.example.codePicasso.domain.chat.dto.response.NotificationResponse;
import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.Notification;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationConnector notificationConnector;
    private final UserConnector userConnector;
    private final ChatConnector chatConnector;

    public NotificationResponse addNotification(NotificationRequest notificationRequest, Long userId) {
        User user = userConnector.findById(userId);
        Chat chat = chatConnector.findById(notificationRequest.messageId());
        Notification notification = notificationRequest.toEntity(user, chat);
        Notification saveNotification = notificationConnector.save(notification);

        return DtoFactory.toNotificationDto(saveNotification);
    }
}
