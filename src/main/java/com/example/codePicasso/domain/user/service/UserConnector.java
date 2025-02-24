package com.example.codePicasso.domain.user.service;

import com.example.codePicasso.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserConnector {
    User save(User user);

    boolean existsByLoginId(String s);

    User findByLoginId(String s);

    User findById(Long userId);

    User findByNickname(String name);
}
