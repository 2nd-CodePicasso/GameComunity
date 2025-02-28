package com.example.codePicasso.domain.chat.entity;

import com.example.codePicasso.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    String imageUrl;

    @Builder
    public Image(User user, String imageUrl) {
        this.user = user;
        this.imageUrl = imageUrl;
    }
}
