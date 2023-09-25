package com.example.backend.dto.WeatherAPI;

import com.example.backend.model.entity.WeatherNotes;


import lombok.Data;

import java.util.List;

@Data
public class WeatherDTO {


    private String currentWeatherCondition; //condition . text
    private String icon;            // condition . icon
    private String temperature;  //temp_c

     private List<WeatherNotes> weatherNotes;
}
