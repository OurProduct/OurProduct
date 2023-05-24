package com.example.bookingservice.service.impl;

import com.example.bookingservice.model.User;
import com.example.bookingservice.model.dto.InsideServiceDto;
import com.example.bookingservice.repository.UserRepository;
import com.example.bookingservice.service.UserRSocketService;
import com.example.bookingservice.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.bookingservice.configuration.GsonConfig.insideServiceDtoUserType;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRSocketService userRSocketService;
    private final UserRepository userRepository;
    private final Gson gson;

    @Override
    public Mono<User> checkUser(String email) {
        return userRepository.findUserByEmail(email)
                .switchIfEmpty(Mono.defer(() ->
                        userRSocketService.checkUser(gson.toJson(InsideServiceDto.<User>builder().data(User.builder().email(email).build()).build()))
                                .map(m -> {
                                    final InsideServiceDto<User> userInsideServiceDto = gson.fromJson(m, insideServiceDtoUserType);
                                    return userInsideServiceDto.getData();
                                })
                                .flatMap(f -> {
                                    final User user = User.builder().email(email).build();
                                    return userRepository.save(user)
                                            .map(l -> {
                                                user.setId(l);
                                                return user;
                                            });
                                })));
    }
}
