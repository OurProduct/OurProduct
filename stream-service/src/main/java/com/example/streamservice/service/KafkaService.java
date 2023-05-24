package com.example.streamservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService implements CommandLineRunner {
    private final KafkaSubscriber kafkaSubscriber;

    @Override
    public void run(String... args) throws Exception {

    }
}
