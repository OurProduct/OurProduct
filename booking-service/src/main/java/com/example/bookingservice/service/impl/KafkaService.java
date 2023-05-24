package com.example.bookingservice.service.impl;

import com.example.bookingservice.model.OperationStats;
import com.example.bookingservice.model.User;
import com.example.bookingservice.model.dto.BuyPlaceRequest;
import com.example.bookingservice.model.dto.ErrorDto;
import com.example.bookingservice.model.dto.KafkaDto;
import com.example.bookingservice.service.BoughtService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.example.bookingservice.configuration.GsonConfig.kafkaDtoUserAndBuyPlaceRequestType;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService implements CommandLineRunner {
    private final KafkaPublisher kafkaPublisher;
    private final KafkaSubscriber kafkaSubscriber;
    private final BoughtService boughtService;
    private final Gson gson;

    private Flux<Void> listenKafkaFunc() {
        return kafkaSubscriber.listenKafkaMessage("bought_place")
                .map(string ->  {
                    final KafkaDto<User, BuyPlaceRequest> kafkaDto = gson.fromJson(string, kafkaDtoUserAndBuyPlaceRequestType);
                    return kafkaDto;
                })
                .flatMap(kafkaDto -> {
                    final User user = kafkaDto.getAuthorData();
                    log.info(kafkaDto.getMessageData().toString());
                    if (user != null && !user.getEmail().isBlank()) {
                        return boughtService.buyPlace(kafkaDto.getMessageData())
                                .flatMap(operationStats -> {
                                    final KafkaDto<User, OperationStats> kafka = KafkaDto.<User, OperationStats>builder()
                                            .authorData(user)
                                            .messageData(operationStats)
                                            .build();
                                    return kafkaPublisher.sendMessage(operationStats.getOperationKey(), gson.toJson(kafka), "sold_place");
                                })
                                .onErrorResume(throwable -> {
                                    log.error("Error: {}, BuyPlaceRequest: {}, User: {}", throwable.getMessage(), kafkaDto.getMessageData(), user);
                                    final KafkaDto<User, Object> error = KafkaDto.<User, Object>builder()
                                            .authorData(user)
                                            .error(ErrorDto.builder().message(throwable.getMessage()).build())
                                            .build();
                                    return kafkaPublisher.sendMessage(error.getAuthorData().getEmail(), gson.toJson(error), "error_place");
                                });
                    }
                    log.warn("User is blank, user: {}", user);
                    return Mono.empty();
                })
                .onErrorResume(throwable -> {
                    log.error("ERROR");
                    throwable.printStackTrace();
                    return listenKafkaFunc();
                });
    }

    @Override
    public void run(String... args) throws Exception {
        listenKafkaFunc()
                .subscribe();
    }
}
