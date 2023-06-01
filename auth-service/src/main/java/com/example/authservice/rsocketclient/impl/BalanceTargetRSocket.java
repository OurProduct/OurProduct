package com.example.authservice.rsocketclient.impl;

import com.example.authservice.model.NodeDto;
import com.example.authservice.model.ServiceType;
import com.example.authservice.rsocketclient.RSocketClient;
import com.example.authservice.rsocketclient.RSocketClientRequester;
import com.google.gson.Gson;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.transport.netty.client.TcpClientTransport;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BalanceTargetRSocket {
    private final List<RSocketClientRequester> rSocketClientRequesters;
    private final List<RSocketClient> rSocketClients;
    private final Gson gson;

    @PostConstruct
    private void initUserLoadBalanceTarget() {
        refreshRSocket(List.of(NodeDto.builder().serviceType(ServiceType.USER).address("localhost").port(7000).build()));
    }

    private List<LoadbalanceTarget> buildRefreshLoadBalanceTarget(List<NodeDto> nodes, ServiceType serviceType) {
        return nodes.stream().filter(nodeDto -> Objects.equals(nodeDto.getServiceType(), serviceType)).map(nodeDto -> LoadbalanceTarget.from(gson.toJson(nodeDto),
                TcpClientTransport.create(nodeDto.getAddress(), nodeDto.getPort()))).collect(Collectors.toList());
    }

    public void refreshRSocket(List<NodeDto> nodes) {
        rSocketClientRequesters.forEach(rSocketClientRequester -> {
            rSocketClientRequester.setLoadBalanceTarget(buildRefreshLoadBalanceTarget(nodes, rSocketClientRequester.getServiceType()));
            rSocketClientRequester.refreshRequester();
        });
    }

    public void refreshUserRSocket(List<NodeDto> nodes) {
        rSocketClientRequesters.forEach(f -> {
            if (Objects.equals(f.getServiceType(), ServiceType.USER)) {
                f.setLoadBalanceTarget(buildRefreshLoadBalanceTarget(nodes, ServiceType.USER));
                f.refreshRequester();
            }
        });
    }

    public Mono<List<LoadbalanceTarget>> getUserLoadBalanceTarget() {
        final List<LoadbalanceTarget> loadbalanceTargets = rSocketClientRequesters.stream().filter(f -> Objects.equals(f.getServiceType(), ServiceType.USER))
                .findFirst().get().getLoadBalanceTarget();
        return Mono.just(loadbalanceTargets);
    }
}
