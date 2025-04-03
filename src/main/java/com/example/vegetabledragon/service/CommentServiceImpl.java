package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.CommentRequest;
import com.example.vegetabledragon.exception.CommentNotPermissionException;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.exception.UserNotFoundException;
import com.example.vegetabledragon.repository.CommentRepository;
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private CheckPermissionServiceImpl checkPermissionService;

    @Override
    public Comment saveComment(Long postId, String sessionUsername, CommentRequest request) throws PostNotFoundException, UserNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setComment(request.getComment());


        // Session 이 다른 거하고 유지되는지를 확인해야 한다.
        if (sessionUsername != null) {
            User user = userRepository.findByUsername(sessionUsername)
                    .orElseThrow(() -> new UserNotFoundException(sessionUsername));
            comment.setUser(user);
            comment.setWriter(user.getAnonymousName());
        } else {
            comment.setUser(null);
            comment.setWriter("익명");
            comment.setPassword(request.getPassword());
        }

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPost(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return commentRepository.findByPost(post);
    }

    @Override
    public Comment updateComment(Long commentId, String sessionUsername, CommentRequest request) throws PostNotFoundException, UserNotFoundException, CommentNotPermissionException {
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // 권한 확인
        checkPermissionService.validateCommentPermission(comment, sessionUsername, request.getPassword());

        // 댓글 수정
        comment.setComment(request.getComment());

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) throws CommentNotPermissionException {
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        checkPermissionService.validateCommentPermission(comment, comment.getWriter(), comment.getPassword());

        commentRepository.delete(comment);
    }
}
