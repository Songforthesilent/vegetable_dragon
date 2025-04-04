package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.CommentRequest;
import com.example.vegetabledragon.exception.InvalidLoginException;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.repository.CommentRepository;
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Comment saveComment(Long postId, String sessionUsername, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setComment(request.getComment());


        // Session 이 다른 거하고 유지되는지를 확인해야 한다.
        if (sessionUsername != null) {
            User user = userRepository.findByUsername(sessionUsername)
                    .orElseThrow(() -> new UsernameNotFoundException(sessionUsername));
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
    public List<Comment> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return commentRepository.findByPost(post);
    }
}
