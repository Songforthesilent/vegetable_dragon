package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.domain.UserFeedback;
import com.example.vegetabledragon.dto.FeedbackRequest;
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserFeedbackRepository;
import com.example.vegetabledragon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFeedbackServiceImpl implements UserFeedbackService {
    private final UserFeedbackRepository userFeedbackRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserFeedback saveFeedback(Long postId, String username, FeedbackRequest request) {
        System.out.println("[DEBUG] saveFeedback() 호출됨 - username: " + username);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 기존 피드백이 있는지 확인
        Optional<UserFeedback> exisitngFeedback = userFeedbackRepository.findByPostAndUser(post, user);

        if (exisitngFeedback.isPresent()) {
            UserFeedback feedback = exisitngFeedback.get();
            feedback.setFakeNews(request.isFakeNews()); // 기존 피드백 수정
            return userFeedbackRepository.save(feedback);
        }

        // 새 피드백 저장
        UserFeedback newFeedback = new UserFeedback(post, user, request.isFakeNews());
        return userFeedbackRepository.save(newFeedback);
    }

    @Override
    public List<UserFeedback> getFeedbacksByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return userFeedbackRepository.findByPost(post);
    }

    @Override
    public Map<String, Double> getFakeNewsFeedbackRatio(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        long fakeNewsCount = userFeedbackRepository.countByPostAndIsFakeNewsTrue(post);
        long trueNewsCount = userFeedbackRepository.countByPostAndIsFakeNewsFalse(post);
        long total = fakeNewsCount + trueNewsCount;

        if (total == 0)
            return Map.of("fakeNewsRatio", 0.0, "trueNewsRatio", 0.0);

        return Map.of(
                "fakeNewsRatio", (double) fakeNewsCount / total,
                "trueNewsRatio", (double) trueNewsCount / total
        );
    }
}
