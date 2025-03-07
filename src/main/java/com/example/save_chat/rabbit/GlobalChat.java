//package com.example.save_chat.rabbit;
//
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Entity
//@NoArgsConstructor
//@Table(name = "global_chats")
//public class GlobalChat {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Long userId;
//
//    private String username;
//
//    private String content;
//
//    private String imageUrl;
//
//    private LocalDateTime createdAt;
//
//    @Builder
//    public GlobalChat(Long userId, String username, String content, String imageUrl, LocalDateTime createdAt) {
//        this.userId = userId;
//        this.username = username;
//        this.content = content;
//        this.imageUrl = imageUrl;
//        this.createdAt = createdAt == null ? LocalDateTime.now():createdAt;
//    }
//}
