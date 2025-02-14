package com.example.codePicasso.domain.users.repository;

import com.example.codePicasso.domain.users.entity.Admin;
import com.example.codePicasso.domain.users.service.AdminConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminConnectorImpl implements AdminConnector {
    private final AdminRepository adminRepository;

    @Override
    public boolean existsByLoginId(String s) {
        return adminRepository.existsByLoginId(s);
    }

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin findByLoginId(String s) {
        return adminRepository.findByLoginId(s);
    }

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElseThrow();
    }
}
