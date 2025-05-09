package com.example.vegetabledragon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor

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
    private String authorUsername; // 로그인한 사용자의 익명 이름

    @Column(nullable = true)
    private String authorEmail; // 해당 사용자의 이메일

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Post(String title, String content, String authorUsername, Category category, String authorEmail) {
        this.title = title;
        this.content = content;
        this.authorUsername = authorUsername;
        this.category =  category;
        this.authorEmail = authorEmail;
    }
}
