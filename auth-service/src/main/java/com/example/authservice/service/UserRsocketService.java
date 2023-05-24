package com.example.authservice.service;

import reactor.core.publisher.Mono;

public interface UserRsocketService {
    Mono<String> saveUser(String userJson);
    Mono<String> findUserByEmail(String userJsonWithEmail);
    Mono<String> login(String userJsonPasswordEmail);
    Mono<String> register(String userJsonEmailPassword);
}
