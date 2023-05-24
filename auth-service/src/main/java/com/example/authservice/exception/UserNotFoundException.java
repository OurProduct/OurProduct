package com.example.authservice.exception;

public class UserNotFoundException extends PublicException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
