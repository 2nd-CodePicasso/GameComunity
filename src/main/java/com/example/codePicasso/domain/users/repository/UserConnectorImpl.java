package com.example.codePicasso.domain.users.repository;

import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.UserConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserConnectorImpl implements UserConnector {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLoginId(String s) {
        return userRepository.existsByLoginId(s);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLoginId(String s) {
        return userRepository.findByLoginId(s).orElseThrow(()->new NotFoundException(ErrorCode.NOT_FOUND_ID));
    }
}
