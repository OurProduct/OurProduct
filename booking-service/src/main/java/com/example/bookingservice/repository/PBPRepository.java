package com.example.bookingservice.repository;

import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.PlaceBookingPrice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PBPRepository {
    Mono<Long> save(PlaceBookingPrice placeBookingPrice);
    Mono<PlaceBookingPrice> updateByBookingPlaceKey(PlaceBookingPrice price);
    Flux<PlaceBookingPrice> findByBookingPlaceKey(String unionKey);
    Flux<PlaceBookingPrice> findByDateAndPrice(LocalDateTime startDate, LocalDateTime endDate, Integer price, String unionKey);
    Flux<BookingPlace> findByPrice(Integer min, Integer max);
}
