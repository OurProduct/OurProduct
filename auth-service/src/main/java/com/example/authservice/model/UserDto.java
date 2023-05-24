package com.example.authservice.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String updateEmail;
    private RoleDto[] roles;

    public UserDto(String email, RoleDto[] roles) {
        this.email = email;
        this.roles = roles;
    }
}
