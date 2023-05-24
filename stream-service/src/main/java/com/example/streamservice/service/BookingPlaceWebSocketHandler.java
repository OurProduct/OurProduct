package com.example.streamservice.service;

import com.example.streamservice.model.dto.BoughtPlaceDto;
import com.example.streamservice.model.dto.BuyPlaceRequest;
import com.example.streamservice.model.dto.KafkaDto;
import com.example.streamservice.model.dto.UserDto;
import com.google.gson.Gson;
import io.netty.buffer.ByteBufAllocator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.adapter.StandardWebSocketSession;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

import static com.example.streamservice.configuration.GsonConfig.kafkaDtoUserAndBoughtType;
import static com.example.streamservice.configuration.GsonConfig.kafkaDtoUserAndObjectType;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingPlaceWebSocketHandler implements WebSocketHandler, CommandLineRunner {

    private final NettyDataBufferFactory nettyDataBuffer = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
    private final HashMap<String, WebSocketSession> webSocketSessionHashMap = new HashMap<>();
    private final KafkaSubscriber kafkaSubscriber;
    private final KafkaPublisher kafkaPublisher;
    private final Gson gson;

    @Override
    public void run(String... args) {
        kafkaSubscriber.listenKafkaMessage("sold_place")
                .flatMap(s -> {
                    final KafkaDto<UserDto, BoughtPlaceDto> kafkaDto = gson.fromJson(s, kafkaDtoUserAndBoughtType);
                    webSocketSessionHashMap.forEach((k, v) -> {
                        v.send(Mono.just(new WebSocketMessage(WebSocketMessage.Type.TEXT, nettyDataBuffer.wrap(gson.toJson(kafkaDto).getBytes()))))
                                .subscribe();
                    });
                    return Mono.empty();
                })
                .subscribe();
        kafkaSubscriber.listenKafkaMessage("error_place")
                .flatMap(string -> {
                    log.info("Error place json: {}", string);
                    final KafkaDto<UserDto, Object> kafkaDto = gson.fromJson(string, kafkaDtoUserAndObjectType);
                    final UserDto userDto = kafkaDto.getAuthorData();
                    if (userDto != null && !userDto.getEmail().isBlank())
                        return webSocketSessionHashMap.get(userDto.getEmail()).send(
                                Mono.just(new WebSocketMessage(WebSocketMessage.Type.TEXT, nettyDataBuffer.wrap(gson.toJson(kafkaDto).getBytes()))));
                    return Mono.empty();
                })
                .subscribe();
    }

    private Mono<Void> sendToTopic(String payload, String sessionId) {
        log.info("payload: {}", payload);
        final BuyPlaceRequest buyPlaceRequest = gson.fromJson(payload, BuyPlaceRequest.class);
        final KafkaDto<UserDto, BuyPlaceRequest> kafkaDto = KafkaDto.<UserDto, BuyPlaceRequest>builder()
                .authorData(buyPlaceRequest.getUser()).messageData(buyPlaceRequest).build();
        final BoughtPlaceDto boughtPlaceDto = kafkaDto.getMessageData().getBoughtPlace();
        log.info("BoughtPlaceDto {}", boughtPlaceDto.toString());
        if (boughtPlaceDto.getPrice() != null && boughtPlaceDto.getDateStart() != null && boughtPlaceDto.getDateEnd() != null) {
            return kafkaPublisher.sendMessage(sessionId, gson.toJson(kafkaDto), "bought_place")
                    .then();
        }
        return Mono.empty();
    }

    @Override
    public List<String> getSubProtocols() {
        return WebSocketHandler.super.getSubProtocols();
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        final HttpHeaders headers = session.getHandshakeInfo().getHeaders();
        final String email = headers.get("user-email").get(0);

        webSocketSessionHashMap.put(email, session);

        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(t -> sendToTopic(t, session.getId()))
                .onErrorResume(s -> {
                    s.printStackTrace();
                    return Mono.empty();
                })
//                .flatMap(t -> session.send(Mono.just(new WebSocketMessage(WebSocketMessage.Type.TEXT, nettyDataBuffer.wrap(t.getBytes())))))
                .then();
    }
}
