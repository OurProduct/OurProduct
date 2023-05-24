package com.example.bookingservice.repository.mapper;

import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.BoughtPlace;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BookingPlaceMapper implements BaseMapper{
    private final BoughtPlaceMapper boughtPlaceMapper;

    public BookingPlace map(Row row, RowMetadata rowMetadata) {
        final Long id = getVal(row, rowMetadata, Long.class, "id");
        final String unionKey = getStringVal(row, rowMetadata, "union_key");
        final String title = getStringVal(row, rowMetadata, "title");
        final String description = getStringVal(row, rowMetadata, "description");
        final String geoPlace = getStringVal(row, rowMetadata, "geo_place");
        final String filePresentationKey = getStringVal(row, rowMetadata, "file_presentation_key");

        final BookingPlace bookingPlace = BookingPlace.builder()
                .id(id)
                .unionKey(unionKey)
                .title(title)
                .description(description)
                .geoPlace(geoPlace)
                .filePresentationKey(filePresentationKey)
                .build();

        return bookingPlace;
    }

    public BookingPlace map(Row row, RowMetadata rowMetadata, boolean withRelationEntity) {
        final Long id = getVal(row, rowMetadata, Long.class, "id");
        final String unionKey = getStringVal(row, rowMetadata, "union_key");
        final String title = getStringVal(row, rowMetadata, "title");
        final String description = getStringVal(row, rowMetadata, "description");
        final String geoPlace = getStringVal(row, rowMetadata, "geo_place");
        final String filePresentationKey = getStringVal(row, rowMetadata, "file_presentation_key");

        final BookingPlace bookingPlace = BookingPlace.builder()
                .id(id)
                .unionKey(unionKey)
                .title(title)
                .description(description)
                .geoPlace(geoPlace)
                .filePresentationKey(filePresentationKey)
                .build();

        if (withRelationEntity) {
            final List<BoughtPlace> boughtPlaces = new ArrayList<>(1);

            final Long boughtPlaceId = getVal(row, rowMetadata, Long.class, "bought_place_id");
            final String boughtPlaceUnionKey = getStringVal(row, rowMetadata, "bought_place_union_key");
            final LocalDateTime boughtPlaceDateStart = getVal(row, rowMetadata, LocalDateTime.class, "date_start");
            final LocalDateTime boughtPlaceDateEnd = getVal(row, rowMetadata, LocalDateTime.class, "date_end");
            final Integer boughtPlacePrice = getVal(row, rowMetadata, Integer.class, "price");

            boughtPlaces.add(BoughtPlace.builder()
                            .id(boughtPlaceId)
                            .unionKey(boughtPlaceUnionKey)
                            .price(boughtPlacePrice)
                            .dateEnd(boughtPlaceDateEnd)
                            .dateStart(boughtPlaceDateStart)
                            .build());

            bookingPlace.setBoughtPlaces(boughtPlaces);
        }

        return bookingPlace;
    }

    public BookingPlace manyMap(List<BookingPlace> bookingPlaces) {
        final BookingPlace bookingPlace = bookingPlaces.get(0);
        final List<BoughtPlace> boughtPlaces = bookingPlaces.stream()
                .map(m -> m.getBoughtPlaces().get(0)).filter(Objects::nonNull).toList();
        bookingPlace.setBoughtPlaces(boughtPlaces);
        return bookingPlace;
    }

    public BookingPlace manyMap(List<BookingPlace> bookingPlaces, boolean manyMainEntity) {

        return null;
    }
}
