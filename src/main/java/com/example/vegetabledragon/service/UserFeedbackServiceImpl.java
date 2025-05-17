package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
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
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserFeedbackRepository;
import com.example.vegetabledragon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
<<<<<<< HEAD
@Slf4j
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
public class UserFeedbackServiceImpl implements UserFeedbackService {
    private final UserFeedbackRepository userFeedbackRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
<<<<<<< HEAD
    public UserFeedbackResponse saveFeedback(Long postId, String username, FeedbackRequest request) throws UserNotFoundException, PostNotFoundException {
//        log.debug("[DEBUG] saveFeedback() 호출됨 - username: " + username);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        // 실제 서비스 시에는 허용시켜야 할 부분
        // Test 시에는 많은 사용자가 없기 때문에 하나의 계정으로 처리할 예정.

//        // 기존 피드백이 있는지 확인
//        Optional<UserFeedback> exisitngFeedback = userFeedbackRepository.findByPostAndUser(post, user);
//
//        if (exisitngFeedback.isPresent()) {
//            UserFeedback feedback = exisitngFeedback.get();
//            feedback.setFakeNews(request.isFakeNews()); // 기존 피드백 수정
//            return userFeedbackRepository.save(feedback);
//        }

        // 새 피드백 저장
        UserFeedback newFeedback = new UserFeedback(post, user, request.isFakeNews());
        UserFeedback saved = userFeedbackRepository.save(newFeedback);

        // Hibernate 로그를 줄이고, 스프링 로그로 생성
//        log.info("[UserfeedbackService_vote] User '{}' voted on Post {} -> fakeNews = {}", user.getUsername(), post.getId(), request.isFakeNews());

        return UserFeedbackResponse.from(saved);
    }

    @Override
    public List<UserFeedbackResponse> getFeedbacksByPost(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return userFeedbackRepository.findByPost(post).
                stream()
                .map(UserFeedbackResponse::from)
                .toList();
    }

    @Override
    public FakeNewsFeedbackRatioResponse getFakeNewsFeedbackRatio(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

        long fakeNewsCount = userFeedbackRepository.countByPostAndIsFakeNewsTrue(post);
        long trueNewsCount = userFeedbackRepository.countByPostAndIsFakeNewsFalse(post);
        long total = fakeNewsCount + trueNewsCount;

<<<<<<< HEAD
        double fakeNewsRatio = 0;
        double trueNewsRatio = 0;

        if (total > 0){
            fakeNewsRatio = (double) fakeNewsCount / total;
            trueNewsRatio = (double) trueNewsCount / total;
        }


        return new FakeNewsFeedbackRatioResponse(fakeNewsCount, trueNewsCount, fakeNewsRatio, trueNewsRatio);
=======
        if (total == 0)
            return Map.of("fakeNewsRatio", 0.0, "trueNewsRatio", 0.0);

        return Map.of(
                "fakeNewsRatio", (double) fakeNewsCount / total,
                "trueNewsRatio", (double) trueNewsCount / total
        );
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    }
}
