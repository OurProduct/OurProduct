package com.example.userservice.repository.mapper;

import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserMapper extends BaseMapper{
    public User userMap(Row row, RowMetadata rowMetadata) {

        final Long id = getVal(row, rowMetadata, Long.class, "id");
        if (id == null)
            return null;
        final String email = getStringVal(row, rowMetadata, "email");
        final String password = getStringVal(row, rowMetadata, "password");
        final String firstName = getStringVal(row, rowMetadata, "first_name");


        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .build();
    }

    public User userMapWithRole(Row row, RowMetadata rowMetadata) {

        final Long userId = getVal(row, rowMetadata, Long.class, "user_id");
        final String userEmail = getStringVal(row, rowMetadata, "user_email");
        final String userPassword = getStringVal(row, rowMetadata, "user_password");
        final String userName = getStringVal(row, rowMetadata, "user_first_name");

        final Long roleId = getVal(row, rowMetadata, Long.class, "role_id");
        final String name = getStringVal(row, rowMetadata, "role_name");

        final Role role = Role.builder()
                .id(roleId)
                .name(name)
                .build();

        return User.builder()
                .id(userId)
                .email(userEmail)
                .password(userPassword)
                .firstName(userName)
                .roles(List.of(role))
                .build();
    }

    public User userMapListToOne(List<User> users) {
        final User firstUser = users.get(0);
        return User.builder()
                .id(firstUser.getId())
                .email(firstUser.getEmail())
                .password(firstUser.getPassword())
                .firstName(firstUser.getFirstName())
                .roles(users.stream().map(m -> m.getRoles().get(0)).toList())
                .build();
    }

    public List<User> userMapListToMany(List<User> users) {
        final Map<User, List<Role>> role = new HashMap<>();

        for (User u : users) {
            if (role.containsKey(u)) {
                final List<Role> roles = role.get(u);
                roles.add(u.getRoles().get(0));
                role.put(u, roles);
            }
        }

        return null;
    }
}
