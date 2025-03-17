package com.example.vegetabledragon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    private String title;
    private String content;
    private String anonymousName;
}
