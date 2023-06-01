package com.example.authservice.service.impl;

import com.example.authservice.configuration.DiscoveryConfig;
import com.example.authservice.model.DiscoveryDto;
import com.example.authservice.model.NodeDto;
import com.example.authservice.model.main.MainDtoResponse;
import com.example.authservice.rsocketclient.impl.BalanceTargetRSocket;
import com.example.authservice.service.DiscoveryService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscoveryServiceImpl implements DiscoveryService {
    private final BalanceTargetRSocket balanceTargetRSocket;
    private final DiscoveryConfig discoveryConfig;
    private final Gson gson;

    @Override
    public Mono<MainDtoResponse<DiscoveryDto>> heartBeat(DiscoveryDto discoveryDto) {
        return balanceTargetRSocket.getUserLoadBalanceTarget().map(l -> {
            final DiscoveryDto discovery = DiscoveryDto.builder()
                    .keyService(discoveryConfig.getKeyService())
                    .nameService(discoveryConfig.getNameService())
                    .nodes(l.stream().map(load -> gson.fromJson(load.getKey(), NodeDto.class)).toList())
                    .build();
            return MainDtoResponse.<DiscoveryDto>builder().data(discovery).build();
        });
    }

    @Override
    public Mono<MainDtoResponse<DiscoveryDto>> changeConfig(DiscoveryDto discoveryDto) {
        log.info("START CHANGE CONFIG {}", discoveryDto);
        balanceTargetRSocket.refreshRSocket(discoveryDto.getNodes());
        discoveryConfig.setKeyService(discoveryConfig.getKeyService());
        discoveryConfig.setNameService(discoveryDto.getNameService());
        return Mono.just(MainDtoResponse.<DiscoveryDto>builder().data(discoveryDto).build());
    }
}
