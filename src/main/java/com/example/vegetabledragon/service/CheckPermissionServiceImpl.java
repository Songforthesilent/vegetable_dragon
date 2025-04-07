package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.exception.CommentNotPermissionException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckPermissionServiceImpl {

    // 익명 댓글의 경우 비밀번호 검증
    public void checkPermissionForAnonymous(Comment comment, String sessionUsername, String password) throws CommentNotPermissionException {
        if (comment.getWriter().equals("익명")){
            if (!comment.getPassword().equals(password)){
                throw new CommentNotPermissionException(password);
            }
        }
    }

    // 일반 댓글의 경우 작성자와 login한 User가 일치하는지 확인
    public void checkPermissionForAuthenticatedUser(Comment comment, String sessionUsername) throws CommentNotPermissionException {
        if (!comment.getWriter().equals(sessionUsername)){
            throw new CommentNotPermissionException(sessionUsername);
        }
    }

    public void validateCommentPermission(Comment comment, String sessionUsername, String password) throws CommentNotPermissionException {
        if (comment.getWriter().equals("익명"))
            checkPermissionForAnonymous(comment, sessionUsername, password);
        else
            checkPermissionForAuthenticatedUser(comment, sessionUsername);
    }
}
