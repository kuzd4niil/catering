package com.example.demo.exception;

public class UserWithThisUsernameExists extends RuntimeException {
    public UserWithThisUsernameExists(String message) {
        super(message);
    }

    public UserWithThisUsernameExists(String message, Throwable cause) {
        super(message, cause);
    }
}
