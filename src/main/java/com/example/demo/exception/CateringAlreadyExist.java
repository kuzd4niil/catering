package com.example.demo.exception;

public class CateringAlreadyExist extends RuntimeException{
    public CateringAlreadyExist(String message) {
        super(message);
    }

    public CateringAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }
}
