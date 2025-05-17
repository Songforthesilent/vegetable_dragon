package com.example.vegetabledragon.exception;

<<<<<<< HEAD
public class InvalidLoginException extends Exception {
=======
public class InvalidLoginException extends RuntimeException {
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    public InvalidLoginException(String message) {
        super(message);
    }
}
