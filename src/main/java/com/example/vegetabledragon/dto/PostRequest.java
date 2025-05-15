package com.example.vegetabledragon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String content;
    private String category;

//    private String anonymousName; 직접 테이블에서 찾아오기로 수정
}
