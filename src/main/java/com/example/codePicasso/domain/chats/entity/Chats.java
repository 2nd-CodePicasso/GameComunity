package com.example.codePicasso.domain.chats.entity;

import com.example.codePicasso.domain.chats.dto.response.ChatsResponse;
import com.example.codePicasso.domain.users.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Chats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String username;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Chats(Long userId, String username, String content) {
        this.userId = userId;
        this.username = username;
        this.content = content;
    }

    public ChatsResponse toDto() {
        return ChatsResponse.builder()
                .chatsId(id)
                .message(content)
                .sender(username)
                .createdAt(createdAt)
                .build();
    }
}
