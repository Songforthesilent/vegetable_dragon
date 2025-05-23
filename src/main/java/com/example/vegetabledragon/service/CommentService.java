package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.dto.CommentRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CommentService {

    Comment saveComment(Long postId, HttpSession session, CommentRequest request);
    List<Comment> getCommentsByPost(Long postId);
    Comment updateComment(Long commentId, HttpSession session, CommentRequest request);
    void deleteComment(Long commentId, String sessionUsername, String password);
}
