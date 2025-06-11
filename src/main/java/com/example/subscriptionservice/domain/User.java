package com.example.subscriptionservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity // JPA가 관리하는 엔티티임을 표시
@Table(name = "users") // DB 테이블명 users와 매핑
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id // PK 컬럼 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략 사용
    private Long id;

    @Column(unique = true, nullable = false) // username은 유니크하며 null 불가
    private String username;

    @Column(nullable = false) // password도 null 불가
    private String password;

    private String email;
}
