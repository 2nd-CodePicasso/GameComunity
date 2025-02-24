package com.example.codePicasso.domain.user.repository;

import com.example.codePicasso.domain.user.entity.Admin;
import com.example.codePicasso.domain.user.service.AdminConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminConnectorImpl implements AdminConnector {

    private final AdminRepository adminRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLoginId(String s) {
        return adminRepository.existsByLoginId(s);
    }

    @Override
    @Transactional
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public Admin findByLoginId(String s) {
        return adminRepository.findByLoginId(s);
    }

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElseThrow();
    }
}
