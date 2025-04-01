package com.example.vegetabledragon.exception;

public class InvalidPageSizeException extends Exception {
    public InvalidPageSizeException(int page, int size) {
        super("Page Number : " + page + " must be non-negative\n" + "Size : " + size +" must be greater than zero");
    }
}
