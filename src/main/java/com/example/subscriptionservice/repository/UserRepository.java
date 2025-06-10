
// UserRepository.java
// 경로: com.example.subscriptionservice.repository.UserRepository

package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Spring Data JPA가 자동 구현해주는 Repository 인터페이스
public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자명을 기준으로 사용자 찾기
    Optional<User> findByUsername(String username);
}
