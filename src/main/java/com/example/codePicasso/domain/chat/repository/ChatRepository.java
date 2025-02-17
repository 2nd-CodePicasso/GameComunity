package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChatRoomId(Long roomId);

    List<Chat> findAllByChatRoomId(Long roomId);
}
