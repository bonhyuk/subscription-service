package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // username으로 User 검색 기능 자동 구현
    Optional<User> findByUsername(String username);
}
