package com.example.authservice.controller;

import com.example.authservice.model.InsideServiceDto;
import com.example.authservice.model.UserDto;
import com.example.authservice.service.UserRsocketService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {
    private final UserRsocketService userRsocketService;
    private final Gson gson;
    @PostMapping("/test1")
    public Mono<String> test1() {
        return Mono.just("asdada");
    }
    @PostMapping("/test2")
    public Mono<String> test2(Principal principal) {
        return Mono.just(principal.getName());
    }
    @PostMapping("/test3")
    public Mono<String> test3(@RequestBody UserDto userDto) {
        return userRsocketService.findUserByEmail(gson.toJson(InsideServiceDto.<UserDto>builder().data(userDto).build()));
//        return Mono.just("?//");
    }
}
