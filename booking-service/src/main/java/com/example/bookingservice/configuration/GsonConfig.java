package com.example.bookingservice.configuration;

import com.example.bookingservice.model.User;
import com.example.bookingservice.model.dto.BuyPlaceRequest;
import com.example.bookingservice.model.dto.InsideServiceDto;
import com.example.bookingservice.model.dto.KafkaDto;
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
    public static Type insideServiceDtoUserType = new TypeToken<InsideServiceDto<User>>(){}.getType();
    public static Type kafkaDtoUserAndBuyPlaceRequestType = new TypeToken<KafkaDto<User, BuyPlaceRequest>>(){}.getType();

}
