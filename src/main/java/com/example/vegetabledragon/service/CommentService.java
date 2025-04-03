package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.dto.CommentRequest;
import com.example.vegetabledragon.exception.CommentNotPermissionException;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.exception.UnauthorizedException;
import com.example.vegetabledragon.exception.UserNotFoundException;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CommentService {
    Comment saveComment(Long postId, HttpSession session, CommentRequest request) throws PostNotFoundException, UserNotFoundException;
    List<Comment> getCommentsByPost(Long postId) throws PostNotFoundException;
    Comment updateComment(Long commentId, HttpSession session, CommentRequest request) throws PostNotFoundException, UserNotFoundException, CommentNotPermissionException;
    void deleteComment(Long commentId, String sessionUsername, String password) throws CommentNotPermissionException, UnauthorizedException;
}
