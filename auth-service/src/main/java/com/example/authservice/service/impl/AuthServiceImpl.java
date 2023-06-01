package com.example.authservice.service.impl;

import com.example.authservice.jwt.JwtUtils;
import com.example.authservice.model.JwtDto;
import com.example.authservice.model.main.MainDtoResponse;
import com.example.authservice.model.user.UserDto;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Gson gson;
    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Override
    public Mono<JwtDto> login(UserDto userDto) {
        return userService.checkLoginUser(userDto)
                .flatMap(u -> {
                    if (u != null) {
                        final String accessToken = jwtUtils.generateAccessToken(u);
                        final String refreshToken = jwtUtils.generateRefreshToken(u);
                        return redisTemplate.opsForValue().set(u.getEmail(), refreshToken)
                                .map(res -> JwtDto.builder().accessToken(accessToken).refreshToken(refreshToken).build());
                    }
                    return Mono.empty();
                });
    }

    @Override
    public Mono<MainDtoResponse<UserDto>> register(UserDto userDto) {
        return userService.register(userDto)
                .map(m -> MainDtoResponse.<UserDto>builder().data(m).build());
    }
}
