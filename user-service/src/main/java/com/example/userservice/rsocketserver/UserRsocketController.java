package com.example.userservice.rsocketserver;

import com.example.userservice.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import static com.example.userservice.configuration.GsonConfig.insideServiceDtoUserType;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserRsocketController {

    private final UserService userService;
    private final Gson gson;

    @MessageMapping("/test1")
    public Mono<String> test1() {
        return Mono.just("TEST1");
    }

    @MessageMapping("/user/save")
    public Mono<String> userSave(String request) {
        return null;
    }

    @MessageMapping("/user/login")
    public Mono<String> login(String request) {
        return userService.login(gson.fromJson(request, insideServiceDtoUserType))
                .map(gson::toJson);
    }

    @MessageMapping("/user/register")
    public Mono<String> register(String request) {
        return userService.register(gson.fromJson(request, insideServiceDtoUserType))
                .map(gson::toJson);
    }

    @MessageMapping("/user/check")
    public Mono<String> checkUser(String request) {
        return userService.check(gson.fromJson(request, insideServiceDtoUserType))
                .map(gson::toJson);
    }
}
