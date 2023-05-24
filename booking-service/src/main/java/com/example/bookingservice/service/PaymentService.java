package com.example.bookingservice.service;

import com.example.bookingservice.model.PayOperation;
import com.example.bookingservice.model.dto.PaymentCredentials;
import reactor.core.publisher.Mono;

public interface PaymentService {
    Mono<PayOperation> buyBookingPlaceOperation(PaymentCredentials paymentCredentials);
}
