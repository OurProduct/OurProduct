package com.example.bookingservice.service;

import com.example.bookingservice.model.OperationStats;
import reactor.core.publisher.Mono;

public interface OperationStatsService {
    Mono<OperationStats> save(OperationStats operationStats);
}
