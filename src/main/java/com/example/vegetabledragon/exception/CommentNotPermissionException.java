package com.example.vegetabledragon.exception;

public class CommentNotPermissionException extends Exception {
    public CommentNotPermissionException(String sessionUsername) {

        super(sessionUsername + " Error : We think that it is not your comments. Please try again");
    }
}
