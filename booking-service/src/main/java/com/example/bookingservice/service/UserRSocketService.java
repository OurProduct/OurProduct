package com.example.bookingservice.service;

import reactor.core.publisher.Mono;

public interface UserRSocketService {
    Mono<String> checkUser(String userEmail);

}
