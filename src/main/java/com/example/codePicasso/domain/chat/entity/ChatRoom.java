package com.example.codePicasso.domain.chat.entity;

import com.example.codePicasso.domain.chat.dto.response.RoomResponse;
import com.example.codePicasso.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private boolean isSecurity;

    private String password;

    @Builder
    public ChatRoom(User user, String name, boolean isSecurity, String password) {
        this.user = user;
        this.name = name;
        this.isSecurity = isSecurity;
        this.password = password == null ? "" : password;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateSecurity(boolean security) {
        this.isSecurity = security;
    }
}
