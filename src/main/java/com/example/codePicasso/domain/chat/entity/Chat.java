package com.example.codePicasso.domain.chat.entity;

import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
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
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String username;

    private String content;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Chat(Long userId, String username, String content) {
        this.userId = userId;
        this.username = username;
        this.content = content;
    }

}
