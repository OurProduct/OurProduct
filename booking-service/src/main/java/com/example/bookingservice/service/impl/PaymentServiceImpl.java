package com.example.bookingservice.service.impl;

import com.example.bookingservice.model.PayOperation;
import com.example.bookingservice.model.dto.PaymentCredentials;
import com.example.bookingservice.repository.PayOperationRepository;
import com.example.bookingservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PayOperationRepository payOperationRepository;
    @Override
    public Mono<PayOperation> buyBookingPlaceOperation(PaymentCredentials paymentCredentials) {
        final PayOperation payOperation = PayOperation.builder().isComplete(true)
                .operationKey(UUID.randomUUID().toString())
                .userEmail(paymentCredentials.getUserEmail())
                .amount(paymentCredentials.getAmount())
                .paymentServiceOperationKey(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .build();
        return payOperationRepository.save(payOperation)
                .map(m -> payOperation);
    }
}
