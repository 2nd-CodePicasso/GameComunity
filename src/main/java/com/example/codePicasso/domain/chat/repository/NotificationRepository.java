package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    Optional<Notification> findTopByChatRoomIdOrderByCreatedTimeDesc(Long roomId);

    List<Notification> findAllByChatRoomId(Long roomId);
}
