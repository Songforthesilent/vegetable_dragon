package com.example.vegetabledragon.exception;

public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
        super("Post's Author User and LoggedUser is different");
    }
}
