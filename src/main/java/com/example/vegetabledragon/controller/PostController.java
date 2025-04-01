package com.example.vegetabledragon.controller;

import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.dto.PostRequest;
import com.example.vegetabledragon.exception.InvalidPageSizeException;
import com.example.vegetabledragon.exception.InvalidPostFieldException;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.service.PostService;
import com.example.vegetabledragon.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService; // PostImpl 에서 PostService -> 상위 인터페이스를 참조하도록 변경.

    // 게시글 작성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) throws InvalidPostFieldException {
        System.out.println("📌 createPost() 실행됨");  // 디버깅 로그
        Post savedPost = postService.createPost(request.getAnonymousName(), request);
        System.out.println("📌 저장된 Post ID: " + savedPost.getId());
        return ResponseEntity.ok(savedPost);
    }

    // 게시글 목록 조회(페이지네이션)
    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws InvalidPageSizeException {
        return ResponseEntity.ok(postService.getAllPosts(page, size));
    }

    // 특정 게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) throws PostNotFoundException {
        return postService.getPostById(postId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

}
