package com.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {
    @PostMapping("/test1")
    public Mono<String> test1() {
        return Mono.just("asdada");
    }
    @PostMapping("/test2")
    public Mono<String> test2(Principal principal) {
        return Mono.just(principal.getName());
    }
}
