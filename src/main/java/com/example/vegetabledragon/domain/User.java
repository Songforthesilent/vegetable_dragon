package com.example.vegetabledragon.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String realName;

    @Column(nullable = false, unique = true)
    private String username; // 아이디 중복불가

    @Column(nullable = false, unique = true)
    private String email; // 이메일 중복 불가

    @Column(nullable = false)
    private String password; // 암호화된 비밀번호

    @Column(nullable = false)
    private LocalDate birthday; // 생년월일

    @Column(nullable = false, unique = true)
    private String anonymousName; // 사이트에서 사용할 익명 이름

    @CreationTimestamp
    private LocalDateTime createdAt;
}
