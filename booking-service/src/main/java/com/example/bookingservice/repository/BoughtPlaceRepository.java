package com.example.bookingservice.repository;

import com.example.bookingservice.model.BoughtPlace;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface BoughtPlaceRepository {
    Mono<Long> save(BoughtPlace boughtPlace);
    Mono<Integer> findCountBoughtPlaceBetweenDate(LocalDateTime startDate, LocalDateTime endDate);
}
