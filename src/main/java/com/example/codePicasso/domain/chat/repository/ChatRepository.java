package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
