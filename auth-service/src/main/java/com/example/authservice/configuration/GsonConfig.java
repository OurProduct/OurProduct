package com.example.authservice.configuration;

import com.example.authservice.model.main.InsideServiceDto;
import com.example.authservice.model.main.MainDtoResponse;
import com.example.authservice.model.user.UserDto;
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

    public static Type insideServiceDtoUserType = new TypeToken<InsideServiceDto<UserDto>>(){}.getType();

    public static Type mainDtoResponseUserType = new TypeToken<MainDtoResponse<UserDto>>(){}.getType();
}
