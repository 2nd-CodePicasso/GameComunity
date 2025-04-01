package com.example.codePicasso.domain.user.entity;

import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users", indexes = @Index(name = "idx_kakao_id", columnList = "kakaoId"))
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String loginId;

    @Column(unique = true)
    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private Long kakaoId;

    private boolean isDeleted;

    @Builder
    public User(String loginId, String nickname, String password, Long kakaoId) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.kakaoId = kakaoId;
        this.userStatus = UserStatus.USER;
        this.isDeleted = false;
    }
}
