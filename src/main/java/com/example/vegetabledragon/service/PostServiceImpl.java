package com.example.vegetabledragon.service;

import com.example.vegetabledragon.apiPayload.code.status.ErrorStatus;
import com.example.vegetabledragon.apiPayload.exception.GeneralException;
import com.example.vegetabledragon.domain.Category;
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.PostRequest;
import com.example.vegetabledragon.repository.CategoryRepository;
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MLService mlService;

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "createdAt");

    @Override
    public Post createPost(String username, PostRequest request, Category postCategory) {
        // 유효성 검사
        validateCreatePostRequest(username, request);

        // 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // 카테고리가 null인 경우 요청에서 다시 찾기
        Category finalCategory = postCategory;
        if (finalCategory == null && request.getCategory() != null) {
            finalCategory = categoryRepository.findByName(request.getCategory());
        }

        // 게시글 생성
        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                user.getAnonymousName(),
                finalCategory,
                user.getEmail()
        );

        // ML 예측 수행
        performMLPrediction(post);

        return postRepository.save(post);
    }

    @Override
    public Page<Post> getAllPosts(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new GeneralException(ErrorStatus.PAGE_NOT_EXIST);
        }
        Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
        return postRepository.findAll(pageable);
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        return Optional.ofNullable(postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND)));
    }

    @Override
    public List<Post> getPostsByCategory(Category category, int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> posts = postRepository.findByCategory(category, pageable);
        return posts.getContent();
    }

    @Override
    public void deletePostById(Long postId, HttpSession session) {
        String userEmail = (String) session.getAttribute("loggedInEmail");

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        if (!post.getAuthorEmail().equals(userEmail)) {
            throw new GeneralException(ErrorStatus.AUTHOR_DIFFERENT_WITH_LOGGEDIN);
        }

        postRepository.deleteById(postId);
    }

    @Override
    public Post updatePost(Long postId, PostRequest request, HttpSession session) {
        String userEmail = (String) session.getAttribute("loggedInEmail");

        // 게시글 조회 및 권한 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        if (!post.getAuthorEmail().equals(userEmail)) {
            throw new GeneralException(ErrorStatus.AUTHOR_DIFFERENT_WITH_LOGGEDIN);
        }

        // 유효성 검사
        validateUpdatePostRequest(request);

        // 필드 업데이트
        post.updateTitle(request.getTitle());
        post.updateContent(request.getContent());

        // 카테고리 업데이트
        if (request.getCategory() != null) {
            Category newCategory = categoryRepository.findByName(request.getCategory());
            post.updateCategory(newCategory);
        }

        return postRepository.save(post);
    }

    @Override
    public List<Post> searchPostsByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title);
    }

    // 유효성 검사 메서드들
    private void validateCreatePostRequest(String username, PostRequest request) {
        if (username == null) {
            throw new GeneralException(ErrorStatus.POST_USERNAME_NOT_EXIST);
        }
        if (request.getTitle() == null) {
            throw new GeneralException(ErrorStatus.POST_TITLE_NOT_EXIST);
        }
        if (request.getContent() == null) {
            throw new GeneralException(ErrorStatus.POST_CONTENT_NOT_EXIST);
        }
        if (request.getCategory() == null) {
            throw new GeneralException(ErrorStatus.POST_CATEGORY_NOT_EXIST);
        }
    }

    private void validateUpdatePostRequest(PostRequest request) {
        if (request.getTitle() == null) {
            throw new GeneralException(ErrorStatus.POST_TITLE_NOT_EXIST);
        }
        if (request.getContent() == null) {
            throw new GeneralException(ErrorStatus.POST_CONTENT_NOT_EXIST);
        }
    }

    // ML 예측 수행
    private void performMLPrediction(Post post) {
        try {
            Map<String, Object> result = mlService.predict(post.getContent());
            Map<String, Object> probabilities = (Map<String, Object>) result.get("probabilities");
            Double probClass1 = ((Number) probabilities.get("class_1")).doubleValue();
            post.updatePrediction(probClass1);
        } catch (Exception e) {
            // 예측 실패 시 무시하고 저장은 유지
            // 로그를 남기고 싶다면 여기에 로깅 추가
        }
    }
}