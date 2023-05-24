package com.example.userservice.repository.mapper;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseMapper {
    protected  <T> T getVal(Row row, RowMetadata metadata, Class<T> tClass, String name) {
        return metadata.contains(name) ? row.get(name, tClass) : null;
    }

    protected String getStringVal(Row row, RowMetadata rowMetadata, String name) {
        try {
            if (rowMetadata.contains(name)) {
                final String res = row.get(name, String.class);
                if (res != null)
                    return res.trim();
            }
            return null;
        } catch (Exception e) {
            log.warn("ERROR PARSE RESULT FROM DB", e);
            return null;
        }
    }
}
