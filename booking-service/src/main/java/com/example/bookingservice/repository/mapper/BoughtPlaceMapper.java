package com.example.bookingservice.repository.mapper;

import com.example.bookingservice.model.BoughtPlace;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BoughtPlaceMapper implements BaseMapper{
    public BoughtPlace map(Row row, RowMetadata rowMetadata) {
        final Long id = getVal(row, rowMetadata, Long.class, "id");
        final Integer price = getVal(row, rowMetadata, Integer.class, "price");
        final LocalDateTime dateStart = getVal(row, rowMetadata, LocalDateTime.class, "date_start");
        final LocalDateTime dateEnd = getVal(row, rowMetadata, LocalDateTime.class, "date_end");

        return BoughtPlace.builder()
                .id(id)
                .price(price)
                .dateStart(dateStart)
                .dateEnd(dateEnd)
                .build();
    }
}
