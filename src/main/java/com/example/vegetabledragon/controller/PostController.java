package com.example.vegetabledragon.controller;

import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.dto.PostRequest;
import com.example.vegetabledragon.exception.*;
import com.example.vegetabledragon.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService; // PostImpl 에서 PostService -> 상위 인터페이스를 참조하도록 변경.

    // 게시글 작성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request, HttpSession session) throws InvalidPostFieldException, UserNotFoundException {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        log.debug("[PostController] createPost() 실행됨");  // 디버깅 로그
        Post savedPost = postService.createPost(loggedInUser, request);
        log.info("[PostController] 저장된 Post ID: " + savedPost.getId());
        return ResponseEntity.ok(savedPost);
    }

    // 게시글 목록 조회(페이지네이션)
    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws InvalidPageSizeException {
        log.info("[PostController] 게시글 목록 조회 - 페이지 : {}, 사이즈 : {}", page, size);
        return ResponseEntity.ok(postService.getAllPosts(page, size));
    }

    // 특정 게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) throws PostNotFoundException {
        log.info("[PostController] 특정 게시글 조회 - 게시글 ID: {}", postId);
        return postService.getPostById(postId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long postId, HttpSession session) throws PostNotFoundException, UnauthorizedException {
        log.info("[PostController] 게시글 삭제 - 게시글 ID: {}", postId);
        postService.deletePostById(postId, session);
        log.info("[PostController] 게시글 삭제 완료");
        return ResponseEntity.noContent().build();
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePostById(@PathVariable Long postId, @RequestBody PostRequest request, HttpSession session) throws PostNotFoundException, InvalidPostFieldException, UnauthorizedException {
        log.info("[PostController] 게시글 수정 - 게시글 ID: {}", postId);
        Post updatedPost = postService.updatePost(postId, request, session);
        log.info("[PostController] 게시글 수정 완료");
        return ResponseEntity.ok(updatedPost);
    }

}
