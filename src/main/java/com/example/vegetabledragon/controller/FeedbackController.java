package com.example.vegetabledragon.controller;

import com.example.vegetabledragon.domain.UserFeedback;
import com.example.vegetabledragon.dto.FeedbackRequest;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.exception.UserNotFoundException;
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
    public ResponseEntity<?> addFeedback(@PathVariable Long postId, @RequestBody FeedbackRequest request, HttpSession session) throws UserNotFoundException, PostNotFoundException {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null)
            return ResponseEntity.status(403).body("로그인이 필요합니다.");

        UserFeedback feedback = userFeedbackService.saveFeedback(postId, loggedInUser, request);
        return ResponseEntity.ok(feedback);
    }

    // 특정 게시글의 모든 피드백 조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<UserFeedback>> getFeedbacks(@PathVariable Long postId) throws PostNotFoundException {
        List<UserFeedback> feedbacks = userFeedbackService.getFeedbacksByPost(postId);
        return ResponseEntity.ok(feedbacks);
    }

    // 특정 게시글의 가짜 뉴스 비율 조회
    @GetMapping("/{postId}/ratio")
    public ResponseEntity<Map<String, Double>> getFakeNewsRatio(@PathVariable Long postId) throws PostNotFoundException {
        Map<String, Double> ratio = userFeedbackService.getFakeNewsFeedbackRatio(postId);
        return ResponseEntity.ok(ratio);
    }
}
