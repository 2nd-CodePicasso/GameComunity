package com.example.codePicasso.domain.user.entity;

import com.example.codePicasso.domain.user.dto.response.UserResponse;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Getter
@NoArgsConstructor
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

    private boolean isDeleted;

    @Builder
    public User(String loginId, String nickname, String password) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.userStatus = UserStatus.USER;
        this.isDeleted = false;
    }



}
