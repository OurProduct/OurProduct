package com.example.authservice.service;

import com.example.authservice.model.JwtDto;
import com.example.authservice.model.main.MainDtoResponse;
import com.example.authservice.model.user.UserDto;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<JwtDto> login(UserDto userDto);

    Mono<MainDtoResponse<UserDto>> register(UserDto userDto);
}
