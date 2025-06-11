package com.example.subscriptionservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subscription_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;         // 구독 상품명
    private String description;  // 상품 설명
    private Integer price;       // 가격 (원 단위)
}
