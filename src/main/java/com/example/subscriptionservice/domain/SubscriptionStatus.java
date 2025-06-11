package com.example.subscriptionservice.domain;

// 구독 상태를 enum으로 관리
public enum SubscriptionStatus {
    ACTIVE,     // 활성 구독 상태
    PAUSED,     // 일시정지 상태
    CANCELLED,  // 취소 상태
    EXPIRED     // 만료 상태
}
