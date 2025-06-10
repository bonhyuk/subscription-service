package com.example.subscriptionservice.scheduler;

import com.example.subscriptionservice.domain.Subscription;
import com.example.subscriptionservice.domain.SubscriptionStatus;
import com.example.subscriptionservice.repository.SubscriptionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SubscriptionScheduler {

    private final SubscriptionRepository repository;

    public SubscriptionScheduler(SubscriptionRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "0 0 0 * * ?")  // 매일 0시 실행
    public void processExpiredSubscriptions() {
        LocalDate today = LocalDate.now();
        List<Subscription> expiringSubs = repository.findByEndDateBeforeAndStatus(today, SubscriptionStatus.ACTIVE);

        for (Subscription sub : expiringSubs) {
            if (sub.isAutoRenew()) {
                sub.setEndDate(sub.getEndDate().plusMonths(1)); // 1개월 연장 예시
            }
            repository.save(sub);
        }
    }
}
