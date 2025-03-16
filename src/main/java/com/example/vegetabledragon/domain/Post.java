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
    private String authorUsername;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Post(String title, String content, String authorUsername) {
        this.title = title;
        this.content = content;
        this.authorUsername = authorUsername;
    }
}
