package com.example.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//Выставленное на аренду место
public class BookingPlace {
    private Long id;
    private String unionKey;//UUID
    private String title;
    private String description;
    private String geoPlace;
    private String filePresentationKey;

    private List<BoughtPlace> boughtPlaces;
}
