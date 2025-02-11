package com.example.codePicasso.domain.users.repository;

import com.example.codePicasso.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
