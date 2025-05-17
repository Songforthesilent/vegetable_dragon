package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.UserFeedback;
<<<<<<< HEAD
import com.example.vegetabledragon.dto.FakeNewsFeedbackRatioResponse;
import com.example.vegetabledragon.dto.FeedbackRequest;
import com.example.vegetabledragon.dto.UserFeedbackResponse;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.exception.UserNotFoundException;
=======
import com.example.vegetabledragon.dto.FeedbackRequest;
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

import java.util.List;
import java.util.Map;

public interface UserFeedbackService {
    // 피드백 저장
<<<<<<< HEAD
    UserFeedbackResponse saveFeedback(Long postId, String username, FeedbackRequest request) throws UserNotFoundException, PostNotFoundException;

    // 특정 게시글의 피드백 조회
    List<UserFeedbackResponse> getFeedbacksByPost(Long postId) throws PostNotFoundException;

    // 특정 게시글의 가짜뉴스 비율 조회
    FakeNewsFeedbackRatioResponse getFakeNewsFeedbackRatio(Long postId) throws PostNotFoundException;
=======
    UserFeedback saveFeedback(Long postId, String username, FeedbackRequest request);

    // 특정 게시글의 피드백 조회
    List<UserFeedback> getFeedbacksByPost(Long postId);

    // 특정 게시글의 가짜뉴스 비율 조회
    Map<String, Double> getFakeNewsFeedbackRatio(Long postId);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
}
