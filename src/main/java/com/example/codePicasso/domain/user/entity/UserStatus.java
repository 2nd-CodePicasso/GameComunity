package com.example.codePicasso.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ADMIN("ROLE_ADMIN"),USER("ROLE_USER");

    private final String roles;
}
