package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.model.dto.InsideServiceDto;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<InsideServiceDto<String>> save(String jsonUser);

    Mono<InsideServiceDto<User>> findUserByEmailJson(InsideServiceDto<User> userInsideServiceDto);

    Mono<InsideServiceDto<User>> updateUser(InsideServiceDto<User> updatedFullUser);

    Mono<InsideServiceDto<User>> login(InsideServiceDto<User> user);

    Mono<InsideServiceDto<User>> register(InsideServiceDto<User> user);

    Mono<InsideServiceDto<User>> check(InsideServiceDto<User> user);

    Mono<InsideServiceDto<User>> updateInfo(InsideServiceDto<User> user);
}
