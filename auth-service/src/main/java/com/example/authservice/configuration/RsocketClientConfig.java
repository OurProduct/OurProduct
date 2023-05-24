package com.example.authservice.configuration;

import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.loadbalance.RoundRobinLoadbalanceStrategy;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RsocketClientConfig {

    @Bean
    public RSocketRequester rSocketRequester() {
        final List<LoadbalanceTarget> loadbalanceTargets = new ArrayList<>();
        loadbalanceTargets.add(LoadbalanceTarget.from("key1", TcpClientTransport.create("localhost", 7000)));
//        loadbalanceTargets.add(LoadbalanceTarget.from("key2", TcpClientTransport.create("localhost", 7001)));
        return RSocketRequester.builder()
                .rsocketConnector(r -> r.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .transports(Mono.just(loadbalanceTargets), new RoundRobinLoadbalanceStrategy());
    }
}
