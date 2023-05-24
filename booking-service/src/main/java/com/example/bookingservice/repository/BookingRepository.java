package com.example.bookingservice.repository;

import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.BoughtPlace;
import com.example.bookingservice.model.PlaceBookingPrice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingRepository {
    Mono<Long> save(BookingPlace bookingPlace);
    Mono<Void> addPlaceBookingPrice(String unionKey, PlaceBookingPrice bookingPrice);
    Mono<Void> addBoughtBookingPlace(String unionKey, BoughtPlace boughtPlace, String email);
    Mono<BookingPlace> findByUnionKey(String unionKey);
    Mono<BookingPlace> findByUnionKeyJoinBoughtPlace(String unionKey);
    Mono<BookingPlace> updateByUnionKey(String unionKey, BookingPlace bookingPlace);
    Flux<BookingPlace> findByLikeDescription(String description);
    Flux<BookingPlace> findByLikeTitle(String title);

}
