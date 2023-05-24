package com.example.bookingservice.service.impl;

import com.example.bookingservice.model.OperationStats;
import com.example.bookingservice.service.OperationStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationStatsServiceImpl implements OperationStatsService {
    @Override
    public Mono<OperationStats> save(OperationStats operationStats) {
        return null;
    }
}
