package com.example.bookingservice.exception;

public class PaymentProcessException extends RuntimeException{
    public PaymentProcessException(String message) {
        super(message);
    }
}
