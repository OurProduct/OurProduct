package com.example.userservice.repository.impl;

import com.example.userservice.model.Role;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final DatabaseClient databaseClient;
    private final RoleMapper roleMapper;

    @Override
    public Mono<Role> save(Role role) {
        final String sql = "INSERT INTO role (name) VALUES (:name) RETURNING id";
        return databaseClient.sql(sql)
                .bind("name", role.getName())
                .map(l -> {
                    final Long id = (Long) l.get("id");
                    role.setId(id);
                    return role;
                })
                .one();
    }

//    @Override
    public Mono<Integer> countRoleByName(String name) {
        final String sql = "SELECT COUNT(*) FROM role WHERE LOWER(name) = :name";
        return databaseClient.sql(sql)
                .bind("name", name.toLowerCase())
                .map(i -> (Integer) i.get(0))
                .one();
    }

    @Override
    public Mono<Role> findByName(String name) {
        final String sql = "SELECT r.id, r.name FROM role r WHERE LOWER(r.name) = :name";
        return databaseClient.sql(sql)
                .bind("name", name.toLowerCase())
                .map(roleMapper::roleMap)
                .one();
    }
}
