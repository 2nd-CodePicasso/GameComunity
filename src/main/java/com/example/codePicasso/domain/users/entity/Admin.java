package com.example.codePicasso.domain.users.entity;

import com.example.codePicasso.domain.users.dto.response.AdminResponse;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Admin extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Builder
    public Admin(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
        this.userStatus = UserStatus.ADMIN;
    }

    public AdminResponse toDto() {
        return AdminResponse.builder()
                .loginId(loginId)
                .build();
    }
}
