package com.example.vegetabledragon.dto;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
<<<<<<< HEAD
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String content;
    private String category;

//    private String anonymousName; 직접 테이블에서 찾아오기로 수정
=======
public class PostRequest {
    private String title;
    private String content;
    private String anonymousName;
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
}
