package com.example.codePicasso.domain.user.service;

import com.example.codePicasso.domain.user.entity.Admin;
import org.springframework.stereotype.Component;

@Component
public interface AdminConnector {
    boolean existsByLoginId(String s);

    Admin save(Admin admin);

    Admin findByLoginId(String s);
}
