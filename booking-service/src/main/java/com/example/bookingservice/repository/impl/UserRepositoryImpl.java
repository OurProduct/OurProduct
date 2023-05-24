package com.example.bookingservice.repository.impl;

import com.example.bookingservice.model.User;
import com.example.bookingservice.repository.UserRepository;
import com.example.bookingservice.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DatabaseClient dc;
    private final UserMapper userMapper;

    @Override
    public Mono<Long> save(User user) {
        final String sql = "INSERT INTO users (email) VALUES (:email)";
        return dc.sql(sql)
                .bind("email", user.getEmail())
                .map(r -> {
                    Long id = (Long) r.get("id");
                    return id;
                })
                .one()
                .onErrorResume(er -> findUserByEmail(user.getEmail())
                        .map(User::getId));
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        final String sql = "SELECT u.id, u.email FROM users u WHERE LOWER(u.email) = :email";
        return dc.sql(sql)
                .bind("email", email.toLowerCase())
                .map(userMapper::map)
                .one();
    }
}
