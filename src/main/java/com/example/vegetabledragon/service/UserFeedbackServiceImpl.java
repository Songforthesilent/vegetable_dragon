package com.example.vegetabledragon.service;

import com.example.vegetabledragon.apiPayload.code.status.ErrorStatus;
import com.example.vegetabledragon.apiPayload.exception.GeneralException;
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.domain.UserFeedback;
import com.example.vegetabledragon.dto.FakeNewsFeedbackRatioResponse;
import com.example.vegetabledragon.dto.FeedbackRequest;
import com.example.vegetabledragon.dto.UserFeedbackResponse;
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
    public UserFeedbackResponse saveFeedback(Long postId, String username, FeedbackRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

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

        return UserFeedbackResponse.from(saved);
    }

    @Override
    public List<UserFeedbackResponse> getFeedbacksByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        return userFeedbackRepository.findByPost(post).
                stream()
                .map(UserFeedbackResponse::from)
                .toList();
    }

    @Override
    public FakeNewsFeedbackRatioResponse getFakeNewsFeedbackRatio(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

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
