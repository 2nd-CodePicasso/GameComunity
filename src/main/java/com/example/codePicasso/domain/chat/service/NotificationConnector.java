package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.NotificationRequest;
import com.example.codePicasso.domain.chat.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public interface NotificationConnector {
    Notification save(Notification notification);
}
