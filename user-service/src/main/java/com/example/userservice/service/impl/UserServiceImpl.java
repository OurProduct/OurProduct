package com.example.userservice.service.impl;

import com.example.userservice.exception.NotValidRequestException;
import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.model.dto.ErrorDto;
import com.example.userservice.model.dto.InsideServiceDto;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import com.google.gson.Gson;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.userservice.configuration.GsonConfig.insideServiceDtoUserType;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
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
                })
                .switchIfEmpty(Mono.defer(() -> Mono.just(InsideServiceDto.<User>builder().status(404)
                        .error(ErrorDto.builder().message("EMAIL OR PASSWORD INCORRECT").build()).build())));
    }
    private void validateRegisterRequest(User user) {
        if (user == null)
            throw new NotValidRequestException("Not valid request, user is null");
        if (user.getEmail() == null)
            throw new NotValidRequestException("User email is null");
        final List<Role> roles = user.getRoles();
        if (roles == null || roles.get(0) == null || StringUtil.isNullOrEmpty(roles.get(0).getName()))
            throw new NotValidRequestException("Name is null, roles: %s".formatted(roles));
     }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<InsideServiceDto<User>> register(InsideServiceDto<User> user) {
        final User u = user.getData();
        validateRegisterRequest(u);
        return userRepository.findByEmail(u.getEmail())
                .map(m -> InsideServiceDto.<User>builder().error(ErrorDto.builder().message("USER ALREADY EXISTS").build()).build())
                .switchIfEmpty(Mono.defer(() ->
                        roleRepository.findByName(u.getRoles().get(0).getName())
                                        .flatMap(role -> saveUserAndRole(u, role)
                                                   .map(us -> InsideServiceDto.<User>builder().data(us).build()))
                                .switchIfEmpty(Mono.defer(() -> Mono.just(
                                        InsideServiceDto.<User>builder().error(ErrorDto.builder().message("ROLE NOT FOUND").status(404).build()).build())))));
    }

    public Mono<User> saveUserAndRole(User user, Role role) {
        return userRepository.save(user)
                .flatMap(l -> userRepository.setUserAndRoleId(user.getEmail(), role.getName())
                        .then(Mono.defer(() -> Mono.just(user))));
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
