package com.example.authservice.service;

import com.example.authservice.model.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> save(UserDto userDto);
    Mono<UserDto> findUserByEmail(UserDto userDto);

    Mono<UserDto> checkLoginUser(UserDto userDto);
    Mono<UserDto> register(UserDto userDto);
}
