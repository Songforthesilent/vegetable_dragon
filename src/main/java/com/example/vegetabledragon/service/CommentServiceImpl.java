package com.example.vegetabledragon.service;

import com.example.vegetabledragon.apiPayload.code.status.ErrorStatus;
import com.example.vegetabledragon.apiPayload.exception.GeneralException;
import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.domain.UserType;
import com.example.vegetabledragon.dto.CommentRequest;
import com.example.vegetabledragon.repository.CommentRepository;
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
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
    private final CheckPermissionServiceImpl checkPermissionService;

    @Override
    public Comment saveComment(Long postId, HttpSession session, CommentRequest request){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));
        // set을 쓰는 것보다, 변수에 저장해서 한 번에 생성자 부르는 게 나아보인다.

        User user = null;
        String writer;
        String password = null;
        UserType userType;

        String sessionUsername = (String) session.getAttribute("loggedInUser");

        // Session 이 다른 거하고 유지되는지를 확인해야 한다.
        if (sessionUsername != null) {
            user = userRepository.findByUsername(sessionUsername)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
            writer = user.getAnonymousName();
            userType = UserType.REGISTERED;

        } else {
            writer = "익명";
            password = request.getPassword();
            userType = UserType.ANONYMOUS;
        }

        Comment comment = new Comment(post, user, writer, request.getComment(), password, userType);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        return commentRepository.findByPost(post);
    }

    @Override
    public Comment updateComment(Long commentId, HttpSession session, CommentRequest request){
        String sessionUsername = (String) session.getAttribute("loggedInUser");
        if (sessionUsername == null) sessionUsername = "익명";

        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        System.out.println("paword" + request.getPassword());
        // 권한 확인
        checkPermissionService.validateCommentPermission(comment, sessionUsername, request.getPassword());

        // 댓글 수정
        comment.updateComment(request.getComment());

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, String sessionUsername, String password){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        // 권한 확인
        checkPermissionService.validateCommentPermission(comment, sessionUsername, password);

        // 권한이 확인되면 댓글 삭제
        commentRepository.deleteById(commentId);

    }
}
