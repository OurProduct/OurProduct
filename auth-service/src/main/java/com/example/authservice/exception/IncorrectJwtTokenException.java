package com.example.authservice.exception;

public class IncorrectJwtTokenException extends RuntimeException {
    public IncorrectJwtTokenException(String message) {
        super(message);
    }
}
