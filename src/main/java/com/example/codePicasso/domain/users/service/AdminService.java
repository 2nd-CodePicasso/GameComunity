package com.example.codePicasso.domain.users.service;

import com.example.codePicasso.domain.users.dto.request.AdminRequest;
import com.example.codePicasso.domain.users.dto.response.AdminResponse;
import com.example.codePicasso.domain.users.entity.Admin;
import com.example.codePicasso.domain.users.repository.AdminRepository;
import com.example.codePicasso.global.config.PasswordEncoder;
import com.example.codePicasso.global.exception.base.DuplicateException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminConnector adminConnector;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AdminResponse addAdmin(AdminRequest adminRequest) {
        if (adminConnector.existsByLoginId(adminRequest.loginId())) {
            throw new DuplicateException(ErrorCode.ID_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(adminRequest.password());

        Admin admin = adminRequest.toEntity(encodedPassword);
        adminConnector.save(admin);
        return admin.toDto();
    }
}
