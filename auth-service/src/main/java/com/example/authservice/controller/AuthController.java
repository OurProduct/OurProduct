package com.example.authservice.controller;

import com.example.authservice.model.JwtDto;
import com.example.authservice.model.MainDtoResponse;
import com.example.authservice.model.UserDto;
import com.example.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Mono<JwtDto> login(@RequestBody UserDto userDto, ServerHttpResponse response) {
        log.info(userDto.toString());
        return authService.login(userDto)
                .map(jwtDto -> {
                    final ResponseCookie cookie = ResponseCookie.from("refresh_token", jwtDto.getRefreshToken()).build();
                    response.addCookie(cookie);
                    jwtDto.setRefreshToken("WAS GENERATED");
                    return jwtDto;
                });
    }

    @PostMapping("/register")
    public Mono<MainDtoResponse<UserDto>> register(@RequestBody UserDto userDto) {

        return authService.register(userDto);
    }

    @PostMapping("/admin")
    public Mono<String> testAdmin(Principal principal) {
        log.info("ADMIN");
        return Mono.just(principal.getName());
    }
}
