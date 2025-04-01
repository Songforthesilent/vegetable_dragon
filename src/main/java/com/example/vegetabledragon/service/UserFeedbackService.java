package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.UserFeedback;
import com.example.vegetabledragon.dto.FeedbackRequest;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface UserFeedbackService {
    // 피드백 저장
    UserFeedback saveFeedback(Long postId, String username, FeedbackRequest request) throws UserNotFoundException, PostNotFoundException;

    // 특정 게시글의 피드백 조회
    List<UserFeedback> getFeedbacksByPost(Long postId) throws PostNotFoundException;

    // 특정 게시글의 가짜뉴스 비율 조회
    Map<String, Double> getFakeNewsFeedbackRatio(Long postId) throws PostNotFoundException;
}
