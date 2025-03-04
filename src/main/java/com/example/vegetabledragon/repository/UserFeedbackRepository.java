package com.example.vegetabledragon.repository;
import com.example.vegetabledragon.domain.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long> {
    
    // 특정 뉴스 게시글에 대해 피드백 개수를 세는 기능
    long countByNewsArticleIdAndFeedbackType(Long newsArticleId, UserFeedback.FeedbackType feedbackType);
}
