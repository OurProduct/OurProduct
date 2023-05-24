package com.example.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsideServiceDto<D> {
    private D data;
    private ErrorDto error;
    private int status;
}
