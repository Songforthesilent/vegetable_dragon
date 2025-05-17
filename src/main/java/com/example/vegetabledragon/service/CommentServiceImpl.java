package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
<<<<<<< HEAD
import com.example.vegetabledragon.domain.UserType;
import com.example.vegetabledragon.dto.CommentRequest;
import com.example.vegetabledragon.exception.CommentNotPermissionException;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.exception.UnauthorizedException;
import com.example.vegetabledragon.exception.UserNotFoundException;
import com.example.vegetabledragon.repository.CommentRepository;
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
<<<<<<< HEAD
    private final CheckPermissionServiceImpl checkPermissionService;

    @Override
    public Comment saveComment(Long postId, HttpSession session, CommentRequest request) throws PostNotFoundException, UserNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        // set을 쓰는 것보다, 변수에 저장해서 한 번에 생성자 부르는 게 나아보인다.

        User user = null;
        String writer;
        String password = null;
        UserType userType;

//        Comment comment = new Comment();
//        comment.setPost(post);
//        comment.setComment(request.getComment());

        String sessionUsername = (String) session.getAttribute("loggedInUser");

        // Session 이 다른 거하고 유지되는지를 확인해야 한다.
        if (sessionUsername != null) {
            user = userRepository.findByUsername(sessionUsername)
                    .orElseThrow(() -> new UserNotFoundException(sessionUsername));
            writer = user.getAnonymousName();
            userType = UserType.REGISTERED;

        } else {
            writer = "익명";
            password = request.getPassword();
            userType = UserType.ANONYMOUS;
        }

        Comment comment = new Comment(post, user, writer, request.getComment(), password, userType);
=======

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

>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        return commentRepository.save(comment);
    }

    @Override
<<<<<<< HEAD
    public List<Comment> getCommentsByPost(Long postId) throws PostNotFoundException {
=======
    public List<Comment> getCommentsByPost(Long postId) {
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return commentRepository.findByPost(post);
    }
<<<<<<< HEAD

    @Override
    public Comment updateComment(Long commentId, HttpSession session, CommentRequest request) throws PostNotFoundException, UserNotFoundException, CommentNotPermissionException {
        String sessionUsername = (String) session.getAttribute("loggedInUser");
        if (sessionUsername == null) sessionUsername = "익명";

        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // 권한 확인
        checkPermissionService.validateCommentPermission(comment, sessionUsername, request.getPassword());

        // 댓글 수정
        comment.updateComment(request.getComment());

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, String sessionUsername, String password) throws CommentNotPermissionException, UnauthorizedException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("comment not find"));

        // 권한 확인
        checkPermissionService.validateCommentPermission(comment, sessionUsername, password);

        // 권한이 확인되면 댓글 삭제
        commentRepository.deleteById(commentId);

    }
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
}
