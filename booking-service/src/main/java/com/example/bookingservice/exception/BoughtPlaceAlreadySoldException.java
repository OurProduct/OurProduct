package com.example.bookingservice.exception;

public class BoughtPlaceAlreadySoldException extends RuntimeException{
    public BoughtPlaceAlreadySoldException(String message) {
        super(message);
    }
}
