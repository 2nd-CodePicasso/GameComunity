package com.example.codePicasso.domain.user.repository;

import com.example.codePicasso.domain.user.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByLoginId(String s);

    Admin findByLoginId(String s);
}
