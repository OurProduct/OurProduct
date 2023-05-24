package com.example.streamservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaDto<A, M> {
    private A authorData;
    private M messageData;
    private ErrorDto error;
}
