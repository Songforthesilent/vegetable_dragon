package com.example.vegetabledragon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String comment;
    private String password; // 비로그인 사용자를 위한 필드
}
