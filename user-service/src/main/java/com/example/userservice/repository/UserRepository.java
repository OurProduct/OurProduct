package com.example.userservice.repository;

import com.example.userservice.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Long> save(User user);

    Mono<User> findByEmail(String email);

    Mono<User> updateUserByEmail(String email, User user);

    Mono<Void> setUserAndRoleId(Long userId, Long roleId);

    Mono<Void> setUserAndRoleId(String userEmail, String roleName);
}
