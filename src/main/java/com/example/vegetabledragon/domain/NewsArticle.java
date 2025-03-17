package com.example.vegetabledragon.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
// lombok을 이용하여 Getter와 Setter 자동생성함.

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="news_articles")
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title; // 게시글의 뉴스 제목

    @Column(columnDefinition = "TEXT")
    private String content; // 게시글의 뉴스 본문

    private Double aiPrediction; // AI 모델이 반환한 가짜뉴스 확률

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 게시글 생성 시간
}
