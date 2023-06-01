package com.example.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscoveryDto {
    private String keyService;
    private String nameService;

    private List<NodeDto> nodes;
}
