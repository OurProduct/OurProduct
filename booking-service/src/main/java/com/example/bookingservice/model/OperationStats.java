package com.example.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationStats {
    private Long id;
    private boolean complete;
    private String operationKey;
    private BookingPlace bookingPlace;
    private BoughtPlace boughtPlace;
}
