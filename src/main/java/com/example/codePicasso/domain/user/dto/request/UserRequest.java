package com.example.codePicasso.domain.user.dto.request;

import com.example.codePicasso.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserRequest(
        String loginId,
        String password,
        String nickname,
        String kakaoToken
) {
    public User toEntity(String encodedPassword, Long kakaoId) {
        return User.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .nickname(nickname)
                .kakaoId(kakaoId)
                .build();
    }
}
