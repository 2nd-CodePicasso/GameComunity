package com.example.codePicasso.domain.user.dto.request;

import com.example.codePicasso.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserRequest(
        String loginId,
        String password,
        String nickname
        ) {

    public User toEntity(String encodedPassword) {
        return User.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .nickname(nickname)
                .build();
    }
}
