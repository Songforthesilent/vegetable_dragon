package com.example.vegetabledragon.repository;
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.domain.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long> {
    List<UserFeedback> findByPost(Post post);

    // 특정 게시글의 가짜 뉴스 여부를 통계로 가져오기
    long countByPostAndIsFakeNewsTrue(Post post);
    long countByPostAndIsFakeNewsFalse(Post post);

    Optional<UserFeedback> findByPostAndUser(Post post, User user);
}