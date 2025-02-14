package com.example.codePicasso.domain.user.repository;

import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
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

    @Override
    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
