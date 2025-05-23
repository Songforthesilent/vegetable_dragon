package com.example.vegetabledragon.controller;

import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.dto.CommentRequest;
import com.example.vegetabledragon.dto.PasswordRequest;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.exception.UnauthorizedException;
import com.example.vegetabledragon.exception.UserNotFoundException;
import com.example.vegetabledragon.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable Long postId,
                                              @RequestBody CommentRequest request,
                                              HttpSession session){
        String sessionUsername = (String) session.getAttribute("loggedInUser"); // 오류 날 거 같음
        Comment saved = commentService.saveComment(postId, session, request);

        return ResponseEntity.ok(saved);
    }

    // 댓글 목록 조회
    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) throws PostNotFoundException {
        List<Comment> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long postId,
                                                 @PathVariable Long commentId,
                                                 @RequestBody CommentRequest request,
                                                 HttpSession session) {
        Comment updated = commentService.updateComment(commentId, session, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId,
                                              @PathVariable Long commentId,
                                              @RequestBody PasswordRequest request,
                                              HttpSession session) {
        String sessionsUsername = (String) session.getAttribute("loggedInUser");

        //            log.info("[CommentController] 댓글 번호 - {} 삭제, 권한 요청 사용자 - {}", commentId, sessionsUsername);
        commentService.deleteComment(commentId, sessionsUsername, request.getPassword());
//            log.info("[CommentController] 댓글 번호 - {} 성공적으로 삭제", commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
