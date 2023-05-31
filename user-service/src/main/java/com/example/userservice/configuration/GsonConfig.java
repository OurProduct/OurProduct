package com.example.userservice.configuration;

import com.example.userservice.model.User;
import com.example.userservice.model.dto.InsideServiceDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;

@Configuration
public class GsonConfig {
    @Bean
    public Gson gson() {
        return new Gson();
    }

    public static Type insideServiceDtoUserType = new TypeToken<InsideServiceDto<User>>() {
    }.getType();

}
