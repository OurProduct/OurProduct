package com.example.userservice.service.impl;

import com.example.userservice.model.User;
import com.example.userservice.model.dto.ErrorDto;
import com.example.userservice.model.dto.InsideServiceDto;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.userservice.configuration.GsonConfig.insideServiceDtoUserType;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Gson gson;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Mono<InsideServiceDto<String>> save(String jsonUser) {
        final InsideServiceDto<User> userDto = gson.fromJson(jsonUser, insideServiceDtoUserType);
//        return userRepository.save(userDto.getData());
        return null;
    }

    @Override
    public Mono<InsideServiceDto<User>> findUserByEmailJson(InsideServiceDto<User> userInsideServiceDto) {
        final User user = userInsideServiceDto.getData();
        return userRepository.findByEmail(user.getEmail())
                .map(m -> InsideServiceDto.<User>builder().data(m).build());
    }

    @Override
    public Mono<InsideServiceDto<User>> updateUser(InsideServiceDto<User> updatedFullUser) {
        final User user = updatedFullUser.getData();
        return userRepository.updateUserByEmail(user.getUpdateEmail(), user)
                .map(m -> InsideServiceDto.<User>builder().data(m).build());
    }

    @Override
    public Mono<InsideServiceDto<User>> login(InsideServiceDto<User> user) {
        final User u = user.getData();
        return userRepository.findByEmail(u.getEmail())
                .map(m -> {
                    if (bCryptPasswordEncoder.matches(u.getPassword(), m.getPassword()))
                        return InsideServiceDto.<User>builder().data(m).build();
                    else
                        return InsideServiceDto.<User>builder().status(404)
                                .error(ErrorDto.builder().message("EMAIL OR PASSWORD INCORRECT").build()).build();
                });
    }

    @Override
    public Mono<InsideServiceDto<User>> register(InsideServiceDto<User> user) {
        final User u = user.getData();
        return userRepository.findByEmail(u.getEmail())
                .map(m -> InsideServiceDto.<User>builder().error(ErrorDto.builder().message("USER ALREADY EXISTS").build()).build())
                .switchIfEmpty(Mono.defer(() ->
                        userRepository.save(u)
                                .map(us -> InsideServiceDto.<User>builder().data(new User()).build())
                ));
    }

    @Override
    public Mono<InsideServiceDto<User>> check(InsideServiceDto<User> user) {
        final User u = user.getData();
        return userRepository.findByEmail(u.getEmail())
                .map(us -> InsideServiceDto.<User>builder().data(us).build())
                .switchIfEmpty(Mono.defer(() ->
                    Mono.just(InsideServiceDto.<User>builder().error(ErrorDto.builder().message("USER NOT FOUND").build()).build())
                ));
    }
}
