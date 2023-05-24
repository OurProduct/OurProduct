package com.example.bookingservice.service.impl;

import com.example.bookingservice.service.UserRSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRSocketServiceImpl implements UserRSocketService {

    private final RSocketRequester rSocketRequester;

    @Override
    public Mono<String> checkUser(String userEmail) {
        return rSocketRequester
                .route("/user/check")
                .data(userEmail)
                .retrieveMono(String.class);
    }
}
