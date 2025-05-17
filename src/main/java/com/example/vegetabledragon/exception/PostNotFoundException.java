package com.example.vegetabledragon.exception;

<<<<<<< HEAD
public class PostNotFoundException extends Exception {
    public PostNotFoundException(Long postId) {

=======
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long postId) {
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        super("Post with id " + postId + " not found");
    }
}
