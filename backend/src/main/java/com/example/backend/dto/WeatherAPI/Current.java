package com.example.backend.dto.WeatherAPI;


import lombok.Data;

@Data
public class Current {
    private String temp_c;
    private Condition condition;


}
