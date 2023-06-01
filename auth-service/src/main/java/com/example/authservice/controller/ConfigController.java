package com.example.authservice.controller;

import com.example.authservice.model.ConfigDto;
import com.example.authservice.model.DiscoveryDto;
import com.example.authservice.model.main.MainDtoResponse;
import com.example.authservice.service.DiscoveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/config")
public class ConfigController {
    private final DiscoveryService discoveryService;

    @PostMapping("/rsocket")
    public Mono<MainDtoResponse<String>> changeAddress(@RequestBody ConfigDto configDto) {
        return Mono.just(MainDtoResponse.<String>builder().data("WAS REFRESH").build());
    }

    @PostMapping("/discovery/heartbeat")
    public Mono<MainDtoResponse<DiscoveryDto>> heartBeat(@RequestBody DiscoveryDto discoveryDto) {
        return discoveryService.heartBeat(discoveryDto);
    }

    @PostMapping("/discovery/change")
    public Mono<MainDtoResponse<DiscoveryDto>> changeConfig(@RequestBody DiscoveryDto discoveryDto) {
        return discoveryService.changeConfig(discoveryDto);
    }
}
