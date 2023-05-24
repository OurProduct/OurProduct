package com.example.bookingservice.repository;

import com.example.bookingservice.model.PayOperation;
import com.example.bookingservice.model.OperationStats;
import reactor.core.publisher.Mono;

public interface PayOperationRepository {
    Mono<String> save(PayOperation payOperation);
    Mono<OperationStats> save(OperationStats operationStats);
}
