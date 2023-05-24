package com.example.bookingservice.exception;

public class PaymentOperationException extends RuntimeException{
    public PaymentOperationException(String message) {
        super(message);
    }
}
