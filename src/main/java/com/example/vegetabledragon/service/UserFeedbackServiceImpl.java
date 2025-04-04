package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.domain.UserFeedback;
import com.example.vegetabledragon.dto.FakeNewsFeedbackRatioResponse;
import com.example.vegetabledragon.dto.FeedbackRequest;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.exception.UserNotFoundException;
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserFeedbackRepository;
import com.example.vegetabledragon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFeedbackServiceImpl implements UserFeedbackService {
    private final UserFeedbackRepository userFeedbackRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserFeedback saveFeedback(Long postId, String username, FeedbackRequest request) throws UserNotFoundException, PostNotFoundException {
        log.debug("[DEBUG] saveFeedback() 호출됨 - username: " + username);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

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
    public List<UserFeedback> getFeedbacksByPost(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return userFeedbackRepository.findByPost(post);
    }

    @Override
    public FakeNewsFeedbackRatioResponse getFakeNewsFeedbackRatio(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        long fakeNewsCount = userFeedbackRepository.countByPostAndIsFakeNewsTrue(post);
        long trueNewsCount = userFeedbackRepository.countByPostAndIsFakeNewsFalse(post);
        long total = fakeNewsCount + trueNewsCount;

        double fakeNewsRatio = 0;
        double trueNewsRatio = 0;

        if (total > 0){
            fakeNewsRatio = (double) fakeNewsCount / total;
            trueNewsRatio = (double) trueNewsCount / total;
        }


        return new FakeNewsFeedbackRatioResponse(fakeNewsCount, trueNewsCount, fakeNewsRatio, trueNewsRatio);
    }
}
