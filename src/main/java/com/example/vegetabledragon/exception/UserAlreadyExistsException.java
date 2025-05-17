package com.example.vegetabledragon.exception;

<<<<<<< HEAD
public class UserAlreadyExistsException extends Exception {
=======
public class UserAlreadyExistsException extends RuntimeException {
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
