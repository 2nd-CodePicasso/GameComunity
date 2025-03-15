package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.Notification;
import com.example.codePicasso.domain.chat.service.NotificationConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationConnectorImpl implements NotificationConnector {
    private final NotificationRepository notificationRepository;

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification findLastByRoomId(Long roomId) {
        return notificationRepository.findTopByChatRoomIdOrderByCreatedTimeDesc(roomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));
    }

    @Override
    public List<Notification> findAllByRoomId(Long roomId) {
        return notificationRepository.findAllByChatRoomId(roomId);
    }
}
