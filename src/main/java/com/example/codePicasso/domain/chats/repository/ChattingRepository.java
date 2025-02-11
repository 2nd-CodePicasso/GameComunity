package com.example.codePicasso.domain.chats.repository;

import com.example.codePicasso.domain.chats.entity.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepository extends JpaRepository<Chatting, Long> {
}
