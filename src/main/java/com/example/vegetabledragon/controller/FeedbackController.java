package com.example.vegetabledragon.controller;

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
import com.example.vegetabledragon.service.UserFeedbackService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final UserFeedbackService userFeedbackService;

    // 게시글에 대한 피드백 추가
    @PostMapping("/{postId}")
<<<<<<< HEAD
    public ResponseEntity<?> addFeedback(@PathVariable Long postId, @RequestBody FeedbackRequest request, HttpSession session) throws UserNotFoundException, PostNotFoundException {
=======
    public ResponseEntity<?> addFeedback(@PathVariable Long postId, @RequestBody FeedbackRequest request, HttpSession session){
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null)
            return ResponseEntity.status(403).body("로그인이 필요합니다.");

<<<<<<< HEAD
        UserFeedbackResponse feedback = userFeedbackService.saveFeedback(postId, loggedInUser, request);
=======
        UserFeedback feedback = userFeedbackService.saveFeedback(postId, loggedInUser, request);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        return ResponseEntity.ok(feedback);
    }

    // 특정 게시글의 모든 피드백 조회
    @GetMapping("/{postId}")
<<<<<<< HEAD
    public ResponseEntity<List<UserFeedbackResponse>> getFeedbacks(@PathVariable Long postId) throws PostNotFoundException {
        List<UserFeedbackResponse> feedbacks = userFeedbackService.getFeedbacksByPost(postId);
=======
    public ResponseEntity<List<UserFeedback>> getFeedbacks(@PathVariable Long postId){
        List<UserFeedback> feedbacks = userFeedbackService.getFeedbacksByPost(postId);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        return ResponseEntity.ok(feedbacks);
    }

    // 특정 게시글의 가짜 뉴스 비율 조회
    @GetMapping("/{postId}/ratio")
<<<<<<< HEAD
    public ResponseEntity<FakeNewsFeedbackRatioResponse> getFakeNewsRatio(@PathVariable Long postId) throws PostNotFoundException {
        FakeNewsFeedbackRatioResponse response = userFeedbackService.getFakeNewsFeedbackRatio(postId);
        return ResponseEntity.ok(response);
=======
    public ResponseEntity<Map<String, Double>> getFakeNewsRatio(@PathVariable Long postId){
        Map<String, Double> ratio = userFeedbackService.getFakeNewsFeedbackRatio(postId);
        return ResponseEntity.ok(ratio);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    }
}
