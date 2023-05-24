package com.example.streamservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCredentialsDto {
    private String card;
    private String userEmail;
    private Integer amount;
}
