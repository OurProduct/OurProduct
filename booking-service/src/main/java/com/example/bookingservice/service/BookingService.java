package com.example.bookingservice.service;

import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.BoughtPlace;
import reactor.core.publisher.Mono;

public interface BookingService {
    Mono<Boolean> check(BookingPlace request, BoughtPlace boughtPlace);
}
