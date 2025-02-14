package com.example.codePicasso.domain.users.service;

import com.example.codePicasso.domain.users.entity.Admin;
import org.springframework.stereotype.Component;

@Component
public interface AdminConnector {
    boolean existsByLoginId(String s);

    Admin save(Admin admin);

    Admin findByLoginId(String s);

    Admin findById(Long adminId);
}
