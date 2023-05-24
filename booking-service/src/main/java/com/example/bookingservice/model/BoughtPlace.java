package com.example.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("bought_place")
//купленно место с датами от и до
public class BoughtPlace {
    private Long id;
    private String unionKey;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Integer price;

    private BookingPlace bookingPlace;

    public LocalDateTime convertDate(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("ss.mm.HH dd.MM.yyyy"));
    }

    public LocalDateTime convertDate(String date, String pattern) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }
}
