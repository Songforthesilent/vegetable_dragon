package com.example.vegetabledragon.domain;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.*;
=======
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Entity
@Getter
<<<<<<< HEAD
@NoArgsConstructor(access = AccessLevel.PROTECTED)
=======
@Setter
@NoArgsConstructor
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
@AllArgsConstructor
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // 글 제목
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT") // 긴 형태의 글 저장
    private String content;

    @Column(nullable = false)
<<<<<<< HEAD
    private String authorUsername; // 로그인한 사용자의 익명 이름

    @Column(nullable = true)
    private String authorEmail; // 해당 사용자의 이메일

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;
=======
    private String authorUsername;
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

    @CreationTimestamp
    private LocalDateTime createdAt;

<<<<<<< HEAD
    @Column(nullable = true)
    private Double prediction; // 클래스 1의 확률

    public Post(String title, String content, String authorUsername, Category category, String authorEmail) {
        this.title = title;
        this.content = content;
        this.authorUsername = authorUsername;
        this.category =  category;
        this.authorEmail = authorEmail;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updatePrediction(Double prediction) {
        this.prediction = prediction;
=======
    public Post(String title, String content, String authorUsername) {
        this.title = title;
        this.content = content;
        this.authorUsername = authorUsername;
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    }
}
