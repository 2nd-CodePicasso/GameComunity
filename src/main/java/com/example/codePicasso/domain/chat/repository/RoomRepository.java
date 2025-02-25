package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<ChatRoom,Long> {
    Optional<ChatRoom> findByName(String roomName);

    Optional<ChatRoom> findByIdAndUserId(Long roomId, Long userId);

    boolean findIsSecurityById(Long roomId);
}
