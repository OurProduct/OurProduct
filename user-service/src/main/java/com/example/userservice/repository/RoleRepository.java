package com.example.userservice.repository;

import com.example.userservice.model.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<Role> save(Role role);

//    Mono<Integer> countRoleByName(String name);

    Mono<Role> findByName(String name);
}
