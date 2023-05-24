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

        final Long userId = row.get(0, Long.class);
        final String userEmail = row.get(1, String.class);
        final String userPassword = row.get(2, String.class);
        final String userName = row.get(3, String.class);

        final Long roleId = row.get(4, Long.class);
        final String name = row.get(5, String.class);

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
