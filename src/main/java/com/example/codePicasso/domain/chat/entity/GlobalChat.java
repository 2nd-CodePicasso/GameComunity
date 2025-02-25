package com.example.codePicasso.domain.chat.entity;

import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@NoArgsConstructor
@Table(name = "global_chats")
public class GlobalChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String username;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public GlobalChat(Long userId, String username, String content) {
        this.userId = userId;
        this.username = username;
        this.content = content;
    }
}
