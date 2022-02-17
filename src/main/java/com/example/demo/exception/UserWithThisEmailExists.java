package com.example.demo.exception;

public class UserWithThisEmailExists extends RuntimeException {
    public UserWithThisEmailExists(String message) {
        super(message);
    }

    public UserWithThisEmailExists(String message, Throwable cause) {
        super(message, cause);
    }
}
