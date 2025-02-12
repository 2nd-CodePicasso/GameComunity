package com.example.codePicasso.domain.users.repository;

import com.example.codePicasso.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String s);

    Optional<User> findByLoginId(String s);
}
