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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    public RoomResponse toDto() {
        return RoomResponse.builder()
                .roomId(id)
                .username(user.getNickname())
                .roomName(name)
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }
}
