package com.example.subscriptionservice.service;

import com.example.subscriptionservice.domain.Subscription;
import com.example.subscriptionservice.domain.SubscriptionStatus;
import com.example.subscriptionservice.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository repository;

    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    // 구독 활성화
    @Transactional
    public Subscription activateSubscription(Long subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);

        if (subscription.getStatus() == SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException("이미 활성화된 구독입니다.");
        }

        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(1)); // 기본 1개월 유효
        return repository.save(subscription);
    }

    // 구독 일시정지
    @Transactional
    public void pauseSubscription(Long subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);

        if (subscription.getStatus() == SubscriptionStatus.ACTIVE) {
            subscription.setStatus(SubscriptionStatus.PAUSED);
            repository.save(subscription);
        } else {
            throw new IllegalStateException("활성 상태가 아닐 때는 일시정지가 불가능합니다.");
        }
    }

    // 구독 취소
    @Transactional
    public void cancelSubscription(Long subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);

        if (subscription.getStatus() == SubscriptionStatus.CANCELLED) {
            throw new IllegalStateException("이미 취소된 구독입니다.");
        }

        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setEndDate(LocalDate.now());
        repository.save(subscription);
    }

    // 자동 갱신 여부 설정
    @Transactional
    public Subscription setAutoRenew(Long subscriptionId, boolean autoRenew) {
        Subscription subscription = getSubscription(subscriptionId);
        subscription.setAutoRenew(autoRenew);
        return repository.save(subscription);
    }

    // 공통: 구독 조회
    private Subscription getSubscription(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("구독을 찾을 수 없습니다. id = " + id));
    }
    public List<Subscription> getSubscriptionsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

}
