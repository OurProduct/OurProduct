package com.example.authservice.service.impl;

import com.example.authservice.service.UserRsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserRSocketServiceImpl implements UserRsocketService {

    private final RSocketRequester userRSocketRequester;

    @Override
    public Mono<String> saveUser(String userJson) {
        return userRSocketRequester.route("/user/save")
                .data(userJson)
                .retrieveMono(String.class);
    }

    @Override
    public Mono<String> findUserByEmail(String userJsonWithEmail) {
        return userRSocketRequester.route("/user/find/email")
                .data(userJsonWithEmail)
                .retrieveMono(String.class);
    }

    @Override
    public Mono<String> login(String userJsonPasswordEmail) {
        return userRSocketRequester.route("/user/login")
                .data(userJsonPasswordEmail)
                .retrieveMono(String.class);
    }

    @Override
    public Mono<String> register(String userJsonEmailPassword) {
        return userRSocketRequester.route("/user/register")
                .data(userJsonEmailPassword)
                .retrieveMono(String.class);
    }
}
