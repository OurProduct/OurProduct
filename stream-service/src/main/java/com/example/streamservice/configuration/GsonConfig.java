package com.example.streamservice.configuration;

import com.example.streamservice.model.dto.BoughtPlaceDto;
import com.example.streamservice.model.dto.KafkaDto;
import com.example.streamservice.model.dto.UserDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;

@Configuration
public class GsonConfig {
    @Bean
    public Gson gson(){return new Gson();}

    public static Type kafkaDtoUserAndBoughtType = new TypeToken<KafkaDto<UserDto, BoughtPlaceDto>>(){}.getType();
    public static Type kafkaDtoUserAndObjectType = new TypeToken<KafkaDto<UserDto, Object>>(){}.getType();
}
