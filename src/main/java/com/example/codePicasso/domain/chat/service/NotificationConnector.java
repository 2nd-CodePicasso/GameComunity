package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.NotificationRequest;
import com.example.codePicasso.domain.chat.dto.response.NotificationResponse;
import com.example.codePicasso.domain.chat.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NotificationConnector {
    Notification save(Notification notification);

    Notification findLastByRoomId(Long roomId);

    List<Notification> findAllByRoomId(Long roomId);
}
