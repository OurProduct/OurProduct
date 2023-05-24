package com.example.bookingservice.repository.impl;

import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.PlaceBookingPrice;
import com.example.bookingservice.repository.PBPRepository;
import com.example.bookingservice.repository.mapper.BookingPlaceMapper;
import com.example.bookingservice.repository.mapper.PBPMapper;
import io.r2dbc.spi.Parameters;
import io.r2dbc.spi.R2dbcType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PBPRepositoryImpl implements PBPRepository {
    private final DatabaseClient dc;
    private final PBPMapper pbpMapper;
    private final BookingPlaceMapper bookingPlaceMapper;

    @Override
    public Mono<Long> save(PlaceBookingPrice placeBookingPrice) {
        final String sql = "INSERT INTO place_booking_price (price, date_start, date_end) VALUES (:price, :dateStart, :dateEnd) RETURNING id";
        return dc.sql(sql)
                .bind("price", Parameters.in(R2dbcType.INTEGER, placeBookingPrice.getPrice()))
                .bind("dateStart", Parameters.in(R2dbcType.TIMESTAMP, placeBookingPrice.convertDate(placeBookingPrice.getDateStart())))
                .bind("dateEnd", Parameters.in(R2dbcType.TIMESTAMP, placeBookingPrice.convertDate(placeBookingPrice.getDateEnd())))
                .map(l -> (Long) l.get("id"))
                .one();
    }

    @Override
    public Flux<PlaceBookingPrice> findByDateAndPrice(LocalDateTime startDate, LocalDateTime endDate, Integer price, String unionKey) {
        log.info("start date {} end date {} price {}", startDate, endDate, price);
        final String sql = "SELECT p.id, p.date_start, p.date_end, p.price FROM place_booking_price p " +
                "WHERE date_start <= :startDate AND date_end >= :endDate AND p.price = :price AND " +
                "   (SELECT pp.price_id FROM place_price AS pp LEFT JOIN booking_place AS bp ON pp.place_id = bp.id WHERE bp.union_key = :unionKey) = p.id";
        return dc.sql(sql)
                .bind("price", price)
                .bind("startDate", startDate)
                .bind("endDate", endDate)
                .bind("unionKey", unionKey)
                .map(pbpMapper::map)
                .all();
    }

    @Override
    public Flux<BookingPlace> findByPrice(Integer min, Integer max) {
        final String sql = "SELECT b.union_key, b.description, b.file_presentation_key, b.geo_place, b.title FROM booking_place AS b WHERE b.id = " +
                "(SELECT pp.place_id FROM place_price AS pp " +
                "LEFT JOIN place_booking_price AS pbp ON pbp.id = pp.price_id WHERE pbp.price >= :min AND pbp.price <= :max)";
        return dc.sql(sql)
                .bind("min", min)
                .bind("max", max)
                .map(bookingPlaceMapper::map)
                .all();
    }

    @Override
    public Mono<PlaceBookingPrice> updateByBookingPlaceKey(PlaceBookingPrice price) {
        final String sql = "UPDATE place_booking_price SET date_start = :dateStart, date_end = :dateEnd, price = :price WHERE id = :id RETURNING *";
        return dc.sql(sql)
                .bind("dateStart", price.getDateStart())
                .bind("dateEnd", price.getDateEnd())
                .bind("price", price.getPrice())
                .bind("id", price.getId())
                .map(pbpMapper::map)
                .one();
    }

    @Override
    public Flux<PlaceBookingPrice> findByBookingPlaceKey(String unionKey) {
        final String sql = "SELECT id, date_start, date_end, price FROM place_booking_price WHERE id = " +
                "(SELECT pp.place_id FROM place_price AS pp LEFT JOIN booking_place AS bp ON pp.place_id = bp.id WHERE bp.union_key = :unionKey)";
        return dc.sql(sql)
                .bind("unionKey", unionKey)
                .map(pbpMapper::map)
                .all();
    }
}
