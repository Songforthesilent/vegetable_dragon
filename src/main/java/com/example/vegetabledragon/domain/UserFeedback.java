package com.example.vegetabledragon.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

<<<<<<< HEAD
import lombok.AccessLevel;
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
<<<<<<< HEAD
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
=======

@Entity
@Getter
@Setter
@NoArgsConstructor
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
@Table(name = "user_feedback")
public class UserFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
<<<<<<< HEAD
    @OnDelete(action = OnDeleteAction.CASCADE)
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
<<<<<<< HEAD
    private User user;  
=======
    private User user;
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

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
