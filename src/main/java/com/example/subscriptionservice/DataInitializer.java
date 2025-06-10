package com.example.subscriptionservice;

import com.example.subscriptionservice.domain.SubscriptionProduct;
import com.example.subscriptionservice.repository.SubscriptionProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SubscriptionProductRepository repository;

    public DataInitializer(SubscriptionProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(SubscriptionProduct.builder()
                .name("베이직 구독")
                .description("기본 구독 서비스")
                .price(10000)
                .build());

        repository.save(SubscriptionProduct.builder()
                .name("프리미엄 구독")
                .description("고급 구독 서비스")
                .price(20000)
                .build());
    }
}
