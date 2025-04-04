package com.example.vegetabledragon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    private String title;
    private String content;
//    private String anonymousName; 직접 테이블에서 찾아오기로 수정
}
