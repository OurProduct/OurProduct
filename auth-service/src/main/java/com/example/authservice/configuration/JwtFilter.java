package com.example.authservice.configuration;

import com.example.authservice.jwt.JwtUtils;
import com.example.authservice.model.JwtCredentials;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter implements WebFilter {
    private final JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final ServerHttpRequest serverHttpRequest = exchange.getRequest();
        final String token = getBearerToken(serverHttpRequest);
        if (token != null && jwtUtils.validateAccessToken(token)) {
            final Claims claims = jwtUtils.getAccessClaims(token);
            final JwtCredentials jwtCredentials = jwtUtils.parseClaims(claims);
            log.info("JWT CREDENTIALS {}", jwtCredentials);

            if (jwtCredentials.getRoles() != null) {
                log.info("SET AUTH TRUE");
                jwtCredentials.setAuthenticated(true);
            }
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(jwtCredentials));

        }
        return chain.filter(exchange);
    }

    private String getBearerToken(ServerHttpRequest serverHttpRequest) {
        try {
            final HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
            final String auth = httpHeaders.get("Authorization").get(0);
            System.out.println("AUTH " + auth);
            if (auth.startsWith("Bearer "))
                return auth.substring(7);
            return null;
        } catch (Exception e) {
            log.warn("ERROR PARSE TOKEN, request: {}", serverHttpRequest.getHeaders());
            return null;
        }
    }
}
