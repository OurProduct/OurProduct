package com.example.authservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class DiscoveryConfig {
    private String keyService = "userKey";
    private String nameService = "nameService";
}
