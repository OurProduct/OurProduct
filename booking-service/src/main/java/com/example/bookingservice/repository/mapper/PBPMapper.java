package com.example.bookingservice.repository.mapper;

import com.example.bookingservice.model.PlaceBookingPrice;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PBPMapper implements BaseMapper {
    public PlaceBookingPrice map(Row row, RowMetadata rowMetadata) {
        final Long id = getVal(row, rowMetadata, Long.class, "id");
        final Integer price = getVal(row, rowMetadata, Integer.class, "price");
        final LocalDateTime dateStart = getVal(row, rowMetadata, LocalDateTime.class, "date_start");
        final LocalDateTime date_end = getVal(row, rowMetadata, LocalDateTime.class, "date_end");

        return PlaceBookingPrice.builder()
                .id(id)
                .price(price)
                .dateStart(dateStart.toString())
                .dateEnd(date_end.toString())
                .build();
    }
}
