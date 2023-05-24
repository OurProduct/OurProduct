package com.example.bookingservice.repository;

import com.example.bookingservice.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Long> save(User user);
    Mono<User> findUserByEmail(String email);
}
