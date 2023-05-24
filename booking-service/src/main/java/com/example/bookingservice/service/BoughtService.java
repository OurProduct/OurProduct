package com.example.bookingservice.service;

import com.example.bookingservice.model.dto.BuyPlaceRequest;
import com.example.bookingservice.model.OperationStats;
import reactor.core.publisher.Mono;

public interface BoughtService {
    Mono<OperationStats> buyPlace(BuyPlaceRequest buyPlaceRequest);
}
