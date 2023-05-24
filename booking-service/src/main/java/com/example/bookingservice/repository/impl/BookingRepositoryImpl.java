package com.example.bookingservice.repository.impl;

import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.BoughtPlace;
import com.example.bookingservice.model.PlaceBookingPrice;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.mapper.BookingPlaceMapper;
import com.example.bookingservice.service.UserService;
import io.r2dbc.spi.Parameters;
import io.r2dbc.spi.R2dbcType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {
    private final UserService userService;
    private final DatabaseClient dc;
    private final BookingPlaceMapper bookingPlaceMapper;
    private final String bookingEntity = "union_key, title, description, geo_place, file_presentation_key";

    @Override
    public Mono<Long> save(BookingPlace bookingPlace) {
        final String sql = "INSERT INTO booking_place (union_key, title, description, geo_place, file_presentation_key) VALUES (:unionKey, :title, :description, :geoPlace, :filePresentationKey) RETURNING id";
        return dc.sql(sql)
                .bind("unionKey", Parameters.in(R2dbcType.VARCHAR, UUID.randomUUID().toString()))
                .bind("title", Parameters.in(R2dbcType.VARCHAR, bookingPlace.getTitle()))
                .bind("description", Parameters.in(R2dbcType.VARCHAR, bookingPlace.getDescription()))
                .bind("geoPlace", Parameters.in(R2dbcType.VARCHAR, bookingPlace.getGeoPlace()))
                .bind("filePresentationKey", Parameters.in(R2dbcType.VARCHAR, bookingPlace.getFilePresentationKey()))
                .map(r -> (Long) r.get("id"))
                .one();
    }

    @Override
    public Mono<Void> addPlaceBookingPrice(String unionKey, PlaceBookingPrice bookingPrice) {
        final String sql = "INSERT INTO place_price AS p (place_id, price_id) VALUES ((SELECT id FROM booking_place WHERE union_key = :unionKey), :priceId)";
        return dc.sql(sql)
                .bind("unionKey", unionKey)
                .bind("priceId", bookingPrice.getId())
                .map(m -> m)
                .one()
                .then();
    }

    @Override
    public Mono<Void> addBoughtBookingPlace(String unionKey, BoughtPlace boughtPlace, String email) {
        log.info("ADD BOUGHT PLACE, unionKey: {}, userEmail: {}, boughtPlace: {}", unionKey, email, boughtPlace.toString());
        final String sql = "INSERT INTO place_bought (place_id, bought_id, user_email) VALUES " +
                "((SELECT id FROM booking_place WHERE union_key = :unionKey), (SELECT id FROM bought_place WHERE union_key = :boughtUnionKey), :email)";
        return userService.checkUser(email)
                .flatMap(u ->
                        dc.sql(sql)
                                .bind("unionKey", unionKey)
                                .bind("boughtUnionKey", boughtPlace.getUnionKey())
                                .bind("email", email.toLowerCase())
                                .map(m -> m)
                                .one()
                                .then()
                )
                .log();
    }

    @Override
    public Mono<BookingPlace> findByUnionKey(String unionKey) {
        final String sql = "SELECT union_key, title, description, geo_place, file_presentation_key FROM booking_place WHERE union_key = :unionKey";
        return dc.sql(sql)
                .bind("unionKey", unionKey)
                .map(bookingPlaceMapper::map)
                .one();
    }

    @Override
    public Flux<BookingPlace> findByLikeDescription(String description) {
        final String sql = "SELECT * FROM booking_place WHERE description LIKE :description";
        return dc.sql(sql)
                .bind("description", "%" + description + "%")
                .map(bookingPlaceMapper::map)
                .all();
    }

    @Override
    public Flux<BookingPlace> findByLikeTitle(String title) {
        final String sql = "SELECT * FROM booking_place WHERE title LIKE :title";
        return dc.sql(sql)
                .bind("title", "%" + title + "%")
                .map(bookingPlaceMapper::map)
                .all();
    }

    @Override
    public Mono<BookingPlace> updateByUnionKey(String unionKey, BookingPlace bookingPlace) {
        final String sql = "UPDATE booking_place SET title = :title, description = :description, geo_place = :geoPlace, " +
                "file_presentation_key = :filePresentationKey WHERE union_key = :unionKey RETURNING *";
        return dc.sql(sql)
                .bind("title", bookingPlace.getTitle())
                .bind("description", bookingPlace.getDescription())
                .bind("geoPlace", bookingPlace.getGeoPlace())
                .bind("filePresentationKey", bookingPlace.getFilePresentationKey())
                .bind("unionKey", bookingPlace.getUnionKey())
                .map(bookingPlaceMapper::map)
                .one();
    }

    @Override
    public Mono<BookingPlace> findByUnionKeyJoinBoughtPlace(String unionKey) {
        final String sql = "SELECT p.id, p.title, p.description, p.geo_place, p.file_presentation_key, p.union_key, b.id, b.union_key, b.date_start, b.date_end, b.price " +
                "FROM booking_place AS p LEFT JOIN place_bought AS pb ON p.id = pb.place_id LEFT JOIN bought_place AS b ON b.id = pb.bought_id WHERE p.union_key = :unionKey";
        return dc.sql(sql)
                .bind("unionKey", unionKey)
                .map(bookingPlaceMapper::map)
                .all()
                .collectList()
                .map(bookingPlaceMapper::manyMap);
    }
}
