
// SubscriptionProductRepository.java
// 경로: com.example.subscriptionservice.repository.SubscriptionProductRepository

package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.domain.SubscriptionProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionProductRepository extends JpaRepository<SubscriptionProduct, Long> {
    List<SubscriptionProduct> findByNameContainingIgnoreCase(String keyword);

    // 가격순 정렬
    List<SubscriptionProduct> findAllByOrderByPriceAsc();
    List<SubscriptionProduct> findAllByOrderByPriceDesc();

}