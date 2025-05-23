package com.example.vegetabledragon.apiPayload.code.status;

import com.example.vegetabledragon.apiPayload.code.BaseErrorCode;
import com.example.vegetabledragon.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버에러, 관리자에게 문의바람."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "요청한 리소스가 없습니다."),

    // CommentPermission
    COMMENT_NOT_PERMISSION(HttpStatus.FORBIDDEN, "COMMENT4031", "댓글에 대한 수정/삭제 권한이 없습니다."),

    // 회원 가입 시
    USER_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "USER4091", "중복된 이메일입니다."),
    USER_NAME_DUPLICATE(HttpStatus.CONFLICT, "USER4092", "중복된 username입니다."),
    USER_ANONYMOUS_DUPLICATE(HttpStatus.CONFLICT, "USER4093", "중복된 익명이름입니다."),
    // 회원 조회 시
    EMAIL_NOT_FOUND(HttpStatus.UNAUTHORIZED, "EMAIL4011", "이메일이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "USER4011", "가입되지 않은 사용자입니다."),
    // 페이지 요청 시
    PAGE_NOT_EXIST(HttpStatus.BAD_REQUEST, "PAGE4001", "요청 페이지 번호는 0 이상이어야 합니다."),

    // 게시글 조회 시
    POST_USERNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "POST4001", "username이 없습니다."),
    POST_TITLE_NOT_EXIST(HttpStatus.BAD_REQUEST, "POST4002", "글의 제목은 필수입니다."),
    POST_CONTENT_NOT_EXIST(HttpStatus.BAD_REQUEST, "POST4003", "글의 Content는 필수입니다."),
    POST_CATEGORY_NOT_EXIST(HttpStatus.BAD_REQUEST, "POST4004", "글의 카테고리는 필수입니다."),
    AUTHOR_DIFFERENT_WITH_LOGGEDIN(HttpStatus.UNAUTHORIZED, "POST4011", "로그인한 사용자와 글의 작성자가 다릅니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
