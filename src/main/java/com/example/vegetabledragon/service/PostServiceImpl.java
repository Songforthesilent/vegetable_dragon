package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Category;
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.PostRequest;
import com.example.vegetabledragon.exception.*;
import com.example.vegetabledragon.repository.CategoryRepository;
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.convention.TestBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// 예외도 발생하는지 처리해야한다.
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MLService mlService;

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "createdAt");

    @Override
    public Post createPost(String username, PostRequest request) throws InvalidPostFieldException, UserNotFoundException {
        if (username == null)
            throw new InvalidPostFieldException(username);
        if (request.getTitle() == null)
            throw new InvalidPostFieldException(request.getTitle());
        if (request.getContent() == null)
            throw new InvalidPostFieldException(request.getContent());
        if (request.getCategory() == null)
            throw new InvalidPostFieldException(request.getCategory());

        // 현재 로그인된 사용자의 Name을 가져오기
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username)));

        // 카테고리 이름으로 Category 객체를 찾음
        Category category = categoryRepository.findByName(request.getCategory());

        Post post = new Post(request.getTitle(), request.getContent(), user.get().getAnonymousName(), category, user.get().getEmail());
//        post.setAuthorUsername(user.get().getAnonymousName()); // 로그인된 사용자의 annonymousName을 넣어준다.

        Post savedPost = postRepository.save(post);

        try {
            Map<String, Object> result = mlService.predict(savedPost.getContent());
            Double prediction = (Double) result.get("prediction");

            savedPost.updatePrediction(prediction);       // prediction 필드 설정
            postRepository.save(savedPost);            // 다시 저장 (update)
        } catch (Exception e) {
            // 예측 실패 시 무시하고 저장은 유지
        }

        return savedPost;
    }
    @Override
    public Page<Post> getAllPosts(int page, int size) throws InvalidPageSizeException {
        if (page < 0 || size <= 0)
            throw new InvalidPageSizeException(page, size);

        Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
        return postRepository.findAll(pageable);
    }
    @Override
    public Optional<Post> getPostById(Long postId) throws PostNotFoundException {
        return Optional.ofNullable(postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId)));
    }

    @Override
    public List<Post> getPostsByCategory(Category category, int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Order.desc("createdAt")));  // 최신순 정렬
        Page<Post> posts = postRepository.findByCategory(category, pageable);
        return posts.getContent();
    }


    @Override
    public void deletePostById(Long postId, HttpSession session) throws PostNotFoundException, UnauthorizedException {
        String loggedInUsername = (String) session.getAttribute("loggedInUser");
        String userEmail = (String) session.getAttribute("loggedInEmail");
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (!post.getAuthorEmail().equals(userEmail))
            throw new UnauthorizedException();

        postRepository.deleteById(postId); // postRepository가 JpaRepository를 확장하고 있으므로, CrudRepository에 있는 deleteById(ID id)를 사용할 수 있다.
    }

    @Override
    public Post updatePost(Long postId, PostRequest request, HttpSession session) throws PostNotFoundException, InvalidPostFieldException, UnauthorizedException {
        // 게시물 존재 확인
        String loggedInUsername = (String) session.getAttribute("loggedInUser");
        String userEmail = (String) session.getAttribute("loggedInEmail");

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (!post.getAuthorEmail().equals(userEmail))
            throw new UnauthorizedException();

        // 수정할 필드의 유효성 검사
        if (request.getTitle() == null)
            throw new InvalidPostFieldException(request.getTitle());

        if (request.getContent() == null)
            throw new InvalidPostFieldException(request.getContent());

        // 카테고리 수정
        if (request.getCategory() != null){
            Category category = categoryRepository.findByName(request.getCategory());
            post.updateCategory(category); // 카테고리 변경
        }

        // 필드 업데이트
        post.updateTitle(request.getTitle());
        post.updateContent(request.getContent());

        // 업데이트된 게시물 저장
        return postRepository.save(post);
    }
}
