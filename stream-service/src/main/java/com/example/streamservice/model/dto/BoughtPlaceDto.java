package com.example.streamservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoughtPlaceDto {
    private Integer price;
    private String unionKey;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
}
