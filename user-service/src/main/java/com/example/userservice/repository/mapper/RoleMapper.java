package com.example.userservice.repository.mapper;

import com.example.userservice.model.Role;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper extends BaseMapper{
    public Role roleMap(Row row, RowMetadata rowMetadata) {
        final Long id = getVal(row, rowMetadata, Long.class, "id");
        final String name = getStringVal(row, rowMetadata, "name");

        return Role.builder().id(id).name(name).build();
    }
}
