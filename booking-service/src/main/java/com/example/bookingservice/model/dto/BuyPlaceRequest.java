package com.example.bookingservice.model.dto;

import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.BoughtPlace;
import com.example.bookingservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyPlaceRequest {
    private BookingPlace bookingPlace;
    private BoughtPlace boughtPlace;
    private PaymentCredentials paymentCredentials;
    private User user;
}
