package com.example.bookingservice.service.impl;

import com.example.bookingservice.exception.PaymentOperationException;
import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.BoughtPlace;
import com.example.bookingservice.model.OperationStats;
import com.example.bookingservice.model.User;
import com.example.bookingservice.model.dto.BuyPlaceRequest;
import com.example.bookingservice.model.dto.PaymentCredentials;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.BoughtPlaceRepository;
import com.example.bookingservice.repository.PayOperationRepository;
import com.example.bookingservice.service.BookingService;
import com.example.bookingservice.service.BoughtService;
import com.example.bookingservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BougthServiceImpl implements BoughtService {
    private final BoughtPlaceRepository boughtPlaceRepository;
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final BookingRepository bookingRepository;
    private final PayOperationRepository payOperationRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<OperationStats> buyPlace(BuyPlaceRequest buyPlaceRequest) {
        final BoughtPlace boughtPlace = buyPlaceRequest.getBoughtPlace();
        final BookingPlace bookingPlace = buyPlaceRequest.getBookingPlace();
        final PaymentCredentials paymentCredentials = buyPlaceRequest.getPaymentCredentials();
        final User user = buyPlaceRequest.getUser();
        return bookingService.check(bookingPlace, boughtPlace)
                .flatMap(bol -> {
                    if (bol)
                        return paymentService.buyBookingPlaceOperation(paymentCredentials);
                    else
                        return Mono.error(new PaymentOperationException("Booking place in request is defferent from booking place in database, booking place: " + bookingPlace.toString()));
                })
                .map(m -> {
                    if (m.isComplete() && !m.getPaymentServiceOperationKey().isBlank()) {
                        return OperationStats.builder().boughtPlace(boughtPlace)
                                .bookingPlace(bookingPlace).operationKey(m.getOperationKey()).complete(true).build();
                    } else
                        throw new PaymentOperationException("Operation doesn't set success, %s".formatted(m.toString()));
                })
                .flatMap(payOperationRepository::save)
                .flatMap(m -> boughtPlaceRepository.save(boughtPlace)
                        .map(v -> m))
                .flatMap(m -> bookingRepository.addBoughtBookingPlace(bookingPlace.getUnionKey(), boughtPlace, user.getEmail())
                        .map(v -> m));
    }
}
