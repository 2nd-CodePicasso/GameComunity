package com.example.codePicasso.domain.users.dto.request;


import com.example.codePicasso.domain.users.entity.Admin;

public record AdminRequest(String loginId, String password) {

    public Admin toEntity(String encodedPassword) {
        return Admin.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .build();
    }
}
