package com.example.vegetabledragon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true) // 비로그인 시에 null로 채움
    private User user;

    @Column(nullable = false)
    private String writer; // anonymouseName, "익명"

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    private String comment;


    private String password; // 비로그인 시 사용자가 입력한 임시 비밀번호

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Comment(Post post, User user, String writer, String comment, String password, UserType userType) {
        this.post = post;
        this.user = user;
        this.writer = writer;
        this.comment = comment;
        this.password = password;
        this.userType = userType;
    }

    public void updateComment(String newComment){
        this.comment = newComment;
    }
}
