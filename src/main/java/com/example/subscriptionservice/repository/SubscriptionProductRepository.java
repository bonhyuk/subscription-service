package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.domain.SubscriptionProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionProductRepository extends JpaRepository<SubscriptionProduct, Long> {
}
