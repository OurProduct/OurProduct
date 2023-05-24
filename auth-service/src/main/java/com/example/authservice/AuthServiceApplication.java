package com.example.authservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.messaging.rsocket.RSocketRequester;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner {

	private final RSocketRequester rSocketRequester;
	private final ReactiveRedisTemplate<String, String> redisTemplate;

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
