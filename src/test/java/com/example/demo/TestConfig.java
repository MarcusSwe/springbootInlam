package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.NumberFormat;

@Configuration
public class TestConfig {

    @Bean
    public NumberFormat defaultCurrencyFormat() {
        return NumberFormat.getCurrencyInstance();
    }

//    @Bean
//    public NumberFormat germanCurrencyFormat() {
//        return NumberFormat.getCurrencyInstance(Locale.GERMAN);
//    }
}

