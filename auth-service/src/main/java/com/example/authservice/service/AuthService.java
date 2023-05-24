package com.example.authservice.service;

import com.example.authservice.model.JwtCredentials;
import com.example.authservice.model.JwtDto;
import com.example.authservice.model.MainDtoResponse;
import com.example.authservice.model.UserDto;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<JwtDto> login(UserDto userDto);

    Mono<MainDtoResponse<UserDto>> register(UserDto userDto);
}
