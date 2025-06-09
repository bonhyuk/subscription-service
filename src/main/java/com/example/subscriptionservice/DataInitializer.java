package com.example.subscriptionservice;

import com.example.subscriptionservice.domain.SubscriptionProduct;
import com.example.subscriptionservice.repository.SubscriptionProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final SubscriptionProductRepository productRepository;

    @PostConstruct
    public void init() {
        if (productRepository.count() == 0) {
            SubscriptionProduct p1 = new SubscriptionProduct();
            p1.setName("프리미엄 구독");
            p1.setDescription("월간 프리미엄 구독 서비스");
            p1.setPrice(15000);
            productRepository.save(p1);

            SubscriptionProduct p2 = new SubscriptionProduct();
            p2.setName("기본 구독");
            p2.setDescription("기본 구독 서비스");
            p2.setPrice(8000);
            productRepository.save(p2);
        }
    }
}
