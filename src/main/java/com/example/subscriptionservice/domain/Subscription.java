// 경로: src/main/java/com/example/subscriptionservice/domain/Subscription.java
package com.example.subscriptionservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // 로그인한 사용자 ID

    @ManyToOne
    @JoinColumn(name = "product_id")
    private SubscriptionProduct product;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean autoRenew;
}
