// User.java
// 경로: com.example.subscriptionservice.domain.User

package com.example.subscriptionservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity // 이 클래스가 JPA 엔티티(데이터베이스 테이블과 매핑)임을 나타냄
@Getter @Setter // Lombok: getter/setter 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드 생성자 자동 생성
@Builder // builder 패턴 사용 가능하게 함
@Table(name = "users")  // 테이블명 user 대신 users 사용
public class User {

    @Id // 기본 키(primary key) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    private Long id;

    @Column(nullable = false, unique = true) // 중복 허용 안됨
    private String username; // 사용자 아이디

    @Column(nullable = false)
    private String password; // 사용자 비밀번호
}