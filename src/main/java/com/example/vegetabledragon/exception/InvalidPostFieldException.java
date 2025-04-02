package com.example.vegetabledragon.exception;

public class InvalidPostFieldException extends Exception {
    public InvalidPostFieldException(String fieldName) {

        super(fieldName + "cannot be null");
    }
}
