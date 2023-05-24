package com.example.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayOperation {
    private Long id;
    private String operationKey;
    private String paymentServiceOperationKey;
    private boolean isComplete;
    private Integer amount;
    private LocalDateTime date;
    private String userEmail;
}
