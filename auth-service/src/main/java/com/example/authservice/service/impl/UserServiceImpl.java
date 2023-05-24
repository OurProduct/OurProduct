package com.example.authservice.service.impl;

import com.example.authservice.exception.UserNotFoundException;
import com.example.authservice.model.InsideServiceDto;
import com.example.authservice.model.UserDto;
import com.example.authservice.service.UserRsocketService;
import com.example.authservice.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.authservice.configuration.GsonConfig.insideServiceDtoUserType;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRsocketService userRsocketService;
    private final Gson gson;
    @Override
    public Mono<Void> save(UserDto userDto) {
        return userRsocketService.saveUser(gson.toJson(userDto))
                .then();
    }

    @Override
    public Mono<UserDto> findUserByEmail(UserDto userDto) {
        return userRsocketService.findUserByEmail(gson.toJson(InsideServiceDto.<UserDto>builder().data(userDto).build()))
                .map(u -> gson.fromJson(u, UserDto.class));
    }

    @Override
    public Mono<UserDto> checkLoginUser(UserDto userDto) {
        final String request = gson.toJson(InsideServiceDto.<UserDto>builder().data(userDto).build());
        return userRsocketService.login(request)
                .map(s -> {
                    final InsideServiceDto<UserDto> response = gson.fromJson(s, insideServiceDtoUserType);
                    return response.getData();
                });
    }

    @Override
    public Mono<UserDto> register(UserDto userDto) {
        final String request = gson.toJson(InsideServiceDto.<UserDto>builder().data(userDto).build());
        return userRsocketService.register(request)
                .map(s -> {
                    final InsideServiceDto<UserDto> response = gson.fromJson(s, insideServiceDtoUserType);
                    return response.getData();
                });
    }
}
