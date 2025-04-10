package com.example.vegetabledragon.dto;

import com.example.vegetabledragon.domain.UserFeedback;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserFeedbackResponse {
    private Long id;
    private String username;
    private boolean isFakeNews;
    private LocalDateTime createdAt;

    public static UserFeedbackResponse from(UserFeedback feedback){
        return new UserFeedbackResponse(
                feedback.getId(),
                feedback.getUser().getUsername(),
                feedback.isFakeNews(),
                feedback.getCreatedAt()
        );
    }
}
