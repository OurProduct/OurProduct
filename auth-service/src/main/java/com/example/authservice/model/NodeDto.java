package com.example.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeDto {
    private String address;
    private int port;
    private String key;
    private ServiceType serviceType;
}
