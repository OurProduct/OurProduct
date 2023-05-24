package com.example.streamservice.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class KafkaPublisher {
    private Map<String, Object> pubConf = new HashMap<>();

    private SenderOptions<String, String> senderOptions;

    private KafkaSender<String, String> kafkaSender;

    @PostConstruct
    private void init() {
        pubConf.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        pubConf.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        pubConf.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        senderOptions = SenderOptions.<String, String>create(pubConf)
                .maxInFlight(1024);

        kafkaSender = KafkaSender.<String, String>create(senderOptions);
    }

    public Mono<Void> sendMessage(String key, String message, String topicName) {
        final String uuid = UUID.randomUUID().toString();
        final SenderRecord<String, String, String> valMessage = SenderRecord.create(topicName, 1, 3600L, key, message, uuid);

        return kafkaSender.send(Mono.just(valMessage))
                .doOnError(e -> log.info("Error send message topic name: " + topicName + " key: " + key + " message: " + message))
                .doOnNext(n -> log.info("Send topicName: " + topicName + ", message: " + message))
                .then();
    }
}
