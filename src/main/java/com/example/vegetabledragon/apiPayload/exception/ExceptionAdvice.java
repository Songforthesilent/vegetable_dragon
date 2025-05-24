package com.example.vegetabledragon.apiPayload.exception;

import com.example.vegetabledragon.apiPayload.ApiResponse;
import com.example.vegetabledragon.apiPayload.code.ErrorReasonDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<?> handleGeneralException(GeneralException e, HttpServletRequest request) {
        ErrorReasonDTO reason = e.getErrorReasonHttpStatus();
        return new ResponseEntity<>(ApiResponse.onFailure(reason.getCode(), reason.getMessage(), null)
                , reason.getHttpStatus());
    }
}
