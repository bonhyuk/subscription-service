package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.domain.Subscription;
import com.example.subscriptionservice.domain.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByEndDateBeforeAndStatus(LocalDate date, SubscriptionStatus status);
    List<Subscription> findByUserId(Long userId);

}

