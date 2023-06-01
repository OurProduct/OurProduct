package com.example.authservice.service;

import com.example.authservice.model.DiscoveryDto;
import com.example.authservice.model.main.MainDtoResponse;
import reactor.core.publisher.Mono;

public interface DiscoveryService {
    Mono<MainDtoResponse<DiscoveryDto>> heartBeat(DiscoveryDto discoveryDto);

    Mono<MainDtoResponse<DiscoveryDto>> changeConfig(DiscoveryDto discoveryDto);
}
