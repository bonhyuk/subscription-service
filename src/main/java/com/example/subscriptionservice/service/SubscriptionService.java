package com.example.subscriptionservice.service;

import com.example.subscriptionservice.domain.Subscription;
import com.example.subscriptionservice.domain.SubscriptionProduct;
import com.example.subscriptionservice.domain.SubscriptionStatus;
import com.example.subscriptionservice.domain.User;
import com.example.subscriptionservice.repository.SubscriptionProductRepository;
import com.example.subscriptionservice.repository.SubscriptionRepository;
import com.example.subscriptionservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionProductRepository subscriptionProductRepository;
    private final UserRepository userRepository;

    /**
     * 구독 생성
     * @param userId 사용자 ID
     * @param productId 구독 상품 ID
     * @return 생성된 Subscription 엔티티
     */
    @Transactional
    public Subscription createSubscription(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자 없음"));
        SubscriptionProduct product = subscriptionProductRepository.findById(productId).orElseThrow(() -> new RuntimeException("상품 없음"));

        Subscription subscription = Subscription.builder()
                .user(user)
                .product(product)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .status(SubscriptionStatus.ACTIVE)
                .autoRenew(false)
                .build();

        return subscriptionRepository.save(subscription);
    }

    /**
     * 구독 활성화
     */
    @Transactional
    public Subscription activateSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("구독 없음"));

        subscription.setStatus(SubscriptionStatus.ACTIVE);
        return subscriptionRepository.save(subscription);
    }

    /**
     * 구독 일시정지
     */
    @Transactional
    public void pauseSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("구독 없음"));
        subscription.setStatus(SubscriptionStatus.PAUSED);
        subscriptionRepository.save(subscription);
    }

    /**
     * 구독 취소
     */
    @Transactional
    public void cancelSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("구독 없음"));
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscriptionRepository.save(subscription);
    }

    /**
     * 자동 갱신 설정 변경
     */
    @Transactional
    public Subscription setAutoRenew(Long subscriptionId, boolean autoRenew) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("구독 없음"));
        subscription.setAutoRenew(autoRenew);
        return subscriptionRepository.save(subscription);
    }

    /**
     * 사용자별 구독 목록 조회
     */
    public List<Subscription> getSubscriptionsByUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }
}
