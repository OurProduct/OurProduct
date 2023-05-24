package com.example.bookingservice.repository.impl;

import com.example.bookingservice.model.BoughtPlace;
import com.example.bookingservice.repository.BoughtPlaceRepository;
import com.example.bookingservice.repository.mapper.BoughtPlaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class BoughtPlaceRepositoryImpl implements BoughtPlaceRepository {
    private final DatabaseClient dc;
    private final BoughtPlaceMapper boughtPlaceMapper;

    @Override
    public Mono<Long> save(BoughtPlace boughtPlace) {
        final String sql = "INSERT INTO bought_place (date_start, date_end, union_key, price) VALUES (:dateStart, :dateEnd, :unionKey, :price) RETURNING id";
        return dc.sql(sql)
                .bind("dateStart", boughtPlace.getDateStart())
                .bind("unionKey", boughtPlace.getUnionKey())
                .bind("dateEnd", boughtPlace.getDateEnd())
                .bind("price", boughtPlace.getPrice())
                .map(l -> (Long) l.get("id"))
                .one();
    }

    @Override
    public Mono<Integer> findCountBoughtPlaceBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        final String sql = "SELECT COUNT(*) FROM bought_place p WHERE (p.date_start < :startDate AND p.date_end > :endDate) OR (p.date_start > :startDate AND p.date_start < :endDate) OR (p.date_end > :startDate AND p.date_end < :endDate) OR (p.date_start = :startDate AND p.date_end = :endDate)";
        return dc.sql(sql)
                .bind("startDate", startDate)
                .bind("endDate", endDate)
                .map(m -> Integer.parseInt(String.valueOf(m.get(0))))
                .one();
    }
}
