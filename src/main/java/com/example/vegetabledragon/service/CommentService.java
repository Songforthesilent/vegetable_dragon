package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.dto.CommentRequest;

import java.util.List;

public interface CommentService {
    Comment saveComment(Long postId, String sessionUsername, CommentRequest request);
    List<Comment> getCommentsByPost(Long postId);
}
