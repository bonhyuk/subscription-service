package com.example.subscriptionservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구독 사용자 (User 엔티티와 다대일 관계)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 구독 상품 (SubscriptionProduct와 다대일 관계)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private SubscriptionProduct product;

    private LocalDate startDate;    // 구독 시작일
    private LocalDate endDate;      // 구독 종료일

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;  // 구독 상태

    private boolean autoRenew;  // 자동 갱신 여부
}
