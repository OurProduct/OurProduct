package com.example.authservice.rsocketclient.impl;

import com.example.authservice.model.ServiceType;
import com.example.authservice.rsocketclient.RSocketClient;
import com.example.authservice.rsocketclient.RSocketClientRequester;
import com.example.authservice.service.UserRsocketService;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.loadbalance.RoundRobinLoadbalanceStrategy;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRSocketClient extends RSocketClientRequester implements UserRsocketService {
    @Getter
    private RSocketRequester userRSocketRequester;

    @PostConstruct
    private void init() {
        this.serviceType = ServiceType.USER;
        this.userRSocketRequester = buildUserRSocketRequester();
    }

    @Override
    public void refreshRequester() {
        log.info("REBUILD USER RSOCKET REQUESTER");
        userRSocketRequester = buildUserRSocketRequester();
    }

    private RSocketRequester buildUserRSocketRequester() {
        return RSocketRequester.builder()
                .rsocketConnector(r -> r.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .transports(Mono.just(loadBalanceTarget), new RoundRobinLoadbalanceStrategy());
    }

    @Override
    public Mono<String> saveUser(String userJson) {
        return userRSocketRequester.route("/user/save")
                .data(userJson)
                .retrieveMono(String.class);
    }

    @Override
    public Mono<String> findUserByEmail(String userJsonWithEmail) {
        return userRSocketRequester.route("/user/find/email")
                .data(userJsonWithEmail)
                .retrieveMono(String.class);
    }

    @Override
    public Mono<String> login(String userJsonPasswordEmail) {
        return userRSocketRequester.route("/user/login")
                .data(userJsonPasswordEmail)
                .retrieveMono(String.class);
    }

    @Override
    public Mono<String> register(String userJsonEmailPassword) {
        return userRSocketRequester.route("/user/register")
                .data(userJsonEmailPassword)
                .retrieveMono(String.class);
    }

    @Override
    public Mono<String> update(String userJson) {
        return userRSocketRequester.route("/user/update/info")
                .data(userJson)
                .retrieveMono(String.class);
    }
}
