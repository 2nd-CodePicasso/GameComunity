package com.example.codePicasso.domain.users.repository;

import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.UserConnector;

public class UserConnectorImpl implements UserConnector {
    UserRepository userRepository;

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean existsByLoginId(String s) {
        return false;
    }

    @Override
    public User findByLoginId(String s) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }
}
