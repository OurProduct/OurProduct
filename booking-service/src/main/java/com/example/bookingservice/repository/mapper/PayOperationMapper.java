package com.example.bookingservice.repository.mapper;

import com.example.bookingservice.model.PayOperation;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PayOperationMapper implements BaseMapper{
    public PayOperation map(Row row, RowMetadata rowMetadata) {
        final Long id = getVal(row, rowMetadata, Long.class, "id");


        return null;
    }
}
