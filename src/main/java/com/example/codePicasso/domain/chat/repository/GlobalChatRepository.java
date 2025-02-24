package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.GlobalChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalChatRepository extends JpaRepository<GlobalChat,Long> {
}
