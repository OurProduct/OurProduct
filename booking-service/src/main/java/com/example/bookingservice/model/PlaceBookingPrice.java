package com.example.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//цена на место с датами от и до
public class PlaceBookingPrice {
    private Long id;
    private Integer price;
    private String dateStart;
    private String dateEnd;

    public LocalDateTime convertDate(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("ss.mm.HH dd.MM.yyyy"));
    }

    public LocalDateTime convertDate(String date, String pattern) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }
}
