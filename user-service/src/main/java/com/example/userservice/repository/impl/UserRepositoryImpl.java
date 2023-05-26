package com.example.userservice.repository.impl;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.repository.mapper.UserMapper;
import io.r2dbc.spi.Parameters;
import io.r2dbc.spi.R2dbcType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DatabaseClient databaseClient;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Mono<Long> save(User user) {
        final String sql = "INSERT INTO users (email, password, first_name, last_name) VALUES (:email, :password , :name, :lastName) RETURNING id";
        user.setPassword(encoder.encode(user.getPassword()));
        return databaseClient.sql(sql)
                .bind("email", Parameters.in(R2dbcType.VARCHAR, user.getEmail()))
                .bind("password", Parameters.in(R2dbcType.VARCHAR, user.getPassword()))
                .bind("name", Parameters.in(R2dbcType.VARCHAR, user.getFirstName()))
                .bind("lastName", Parameters.in(R2dbcType.VARCHAR, user.getLastName()))
                .map(r -> {
                    user.setPassword(null);
                    return Long.parseLong(String.valueOf(r.get("id")));
                })
                .one();
    }

    @Override
    public Mono<User> findByEmail(String email) {
        final String sql = "SELECT u.id, u.email, u.password, u.first_name FROM users u WHERE LOWER(u.email) = :email";
        return databaseClient.sql(sql)
                .bind("email", Parameters.in(R2dbcType.VARCHAR, email.toLowerCase()))
                .map(userMapper::userMap)
                .one();
    }

    @Override
    public Mono<User> updateUserByEmail(String email, User user) {
        final String sql = "UPDATE users SET email = :email, password = :password, first_name = :firstName, last_name = :lastName WHERE LOWER (email) = :emailSearch RETURNING id, email, password, first_name, last_name";
        return databaseClient.sql(sql)
                .bind("email", Parameters.in(R2dbcType.VARCHAR, user.getUpdateEmail()))
                .bind("password", Parameters.in(R2dbcType.VARCHAR, user.getPassword()))
                .bind("firstName", Parameters.in(R2dbcType.VARCHAR, user.getFirstName()))
                .bind("lastName", Parameters.in(R2dbcType.VARCHAR, user.getLastName()))
                .bind("emailSearch", Parameters.in(R2dbcType.VARCHAR, email.toLowerCase()))
                .map(userMapper::userMap)
                .one();
    }

    @Override
    public Mono<Void> setUserAndRoleId(Long userId, Long roleId) {
        final String sql = "INSERT INTO user_and_role (user_id, role_id) VALUES (:userId, :roleId)";
        return databaseClient.sql(sql)
                .bind("userId", userId)
                .bind("roleId", roleId)
                .then();
    }

    @Override
    public Mono<Void> setUserAndRoleId(String userEmail, String roleName) {
        final String sql = "INSERT INTO user_and_role (user_id, role_id) VALUES ((SELECT u.id FROM users u " +
                "WHERE LOWER(u.email) = :email), (SELECT r.id FROM role r WHERE LOWER(r.name) = :name))";
        return databaseClient.sql(sql)
                .bind("email", userEmail.toLowerCase())
                .bind("name", roleName.toLowerCase())
                .then();
    }

    @Override
    public Mono<User> findByEmailWithRole(String email) {
        final String sql = "SELECT u.id AS user_id, u.email AS user_email, u.password AS user_password, r.id, r.name AS role_name " +
                "FROM users AS u LEFT JOIN user_and_role AS uar ON u.id = uar.user_id " +
                "LEFT JOIN role AS r ON r.id = uar.role_id WHERE LOWER(u.email) = :email";
        return databaseClient.sql(sql)
                .bind("email", email.toLowerCase())
                .map(userMapper::userMapWithRole)
                .one();
    }
}
