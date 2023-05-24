package com.example.bookingservice.service;

import com.example.bookingservice.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> checkUser(String email);
}
