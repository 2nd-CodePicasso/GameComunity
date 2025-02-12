package com.example.codePicasso.domain.users.entity;

import com.example.codePicasso.domain.users.dto.request.UserRequest;
import com.example.codePicasso.domain.users.dto.response.UserResponse;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String nickname;

    private String password;

    private boolean isDeleted;

    @Builder
    public User(String loginId, String nickname, String password) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.isDeleted = false;
    }



    public UserResponse toDto() {
        return UserResponse.builder().
                loginId(loginId).
                nickname(nickname).
                build();
    }

}
