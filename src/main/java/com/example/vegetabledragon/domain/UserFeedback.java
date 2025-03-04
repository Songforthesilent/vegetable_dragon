package com.example.vegetabledragon.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_feedback")
public class UserFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @ManyToOne
    @JoinColumn(name = "news_id", nullable = false) // 뉴스 기사 ID를 외래키로 적용
    private NewsArticle newsArticle;

    @Column(nullable = false, length = 255)
    private String userId; // 사용자의 ID(ex. dndjd, qwerty)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackType feedbackType; // 사람들이 게시글에 남긴 반응(가짜다 vs 진짜다)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Enum 정의
    public enum FeedbackType {
        FAKE, REAL
    }

}
