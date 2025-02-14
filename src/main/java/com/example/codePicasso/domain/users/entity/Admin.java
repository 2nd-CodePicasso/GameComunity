package com.example.codePicasso.domain.users.entity;

import com.example.codePicasso.domain.users.dto.response.AdminResponse;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Builder
    public Admin(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public AdminResponse toDto() {
        return AdminResponse.builder()
                .loginId(loginId)
                .build();
    }
}
