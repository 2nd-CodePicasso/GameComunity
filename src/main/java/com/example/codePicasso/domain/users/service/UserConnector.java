package com.example.codePicasso.domain.users.service;

import com.example.codePicasso.domain.users.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserConnector {
    User save(User user);

    boolean existsByLoginId(String s);

    User findByLoginId(String s);

    User findById(Long id);
}
