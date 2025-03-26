package com.example.vegetabledragon.controller;

import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.dto.CommentRequest;
import com.example.vegetabledragon.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    public ResponseEntity<Comment> addComment(@PathVariable Long postId,
                                              @RequestBody CommentRequest request,
                                              HttpSession session) {
        String sessionUsername = (String) session.getAttribute("loggedInUser"); // 오류 날 거 같음
        Comment saved = commentService.saveComment(postId, sessionUsername, request);

        return ResponseEntity.ok(saved);
    }

    // 댓글 목록 조회
    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId){
        List<Comment> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }
}
