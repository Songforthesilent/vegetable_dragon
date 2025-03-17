package com.example.vegetabledragon.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean isFakeNews; // true면 가짜뉴스, false면 진짜뉴스

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public UserFeedback(Post post, User user, boolean isFakeNews){
        this.post = post;
        this.user = user;
        this.isFakeNews = isFakeNews;
    }
}
