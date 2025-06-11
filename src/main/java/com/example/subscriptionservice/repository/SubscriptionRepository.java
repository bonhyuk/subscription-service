package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.domain.Subscription;
import com.example.subscriptionservice.domain.SubscriptionStatus;
import com.example.subscriptionservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    // 만료된 구독 찾기
    List<Subscription> findByEndDateBeforeAndStatus(LocalDate date, SubscriptionStatus status);

    // 특정 사용자 구독 목록 조회
    List<Subscription> findByUserId(Long userId);

}
