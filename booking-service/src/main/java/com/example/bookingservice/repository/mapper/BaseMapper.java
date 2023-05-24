package com.example.bookingservice.repository.mapper;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

public interface BaseMapper {
    default  <T> T getVal(Row row, RowMetadata metadata, Class<T> tClass, String name) {
        return metadata.contains(name) ? row.get(name, tClass) : null;
    }

    default String getStringVal(Row row, RowMetadata rowMetadata, String name) {
        if (rowMetadata.contains(name)) {
            final String res = row.get(name, String.class);
            if (res != null)
                return res.trim();
        }
        return null;
    }
}
