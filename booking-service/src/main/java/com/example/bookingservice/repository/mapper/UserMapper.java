package com.example.bookingservice.repository.mapper;

import com.example.bookingservice.model.User;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements BaseMapper{
    public User map(Row row, RowMetadata rowMetadata) {
        final Long id = getVal(row, rowMetadata, Long.class, "id");
        final String email = getStringVal(row, rowMetadata, "email");

        return User.builder()
                .id(id)
                .email(email)
                .build();
    }
}
