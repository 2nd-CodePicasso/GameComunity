package com.example.codePicasso.domain.user.dto.request;


import com.example.codePicasso.domain.user.entity.Admin;

public record AdminRequest(String loginId, String password) {

    public Admin toEntity(String encodedPassword) {
        return Admin.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .build();
    }
}
