package com.example.streamservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingPlace {
    private Long id;
    private String unionKey;//UUID
    private String title;
    private String description;
    private String geoPlace;
    private String filePresentationKey;
}
