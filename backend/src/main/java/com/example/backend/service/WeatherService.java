package com.example.backend.service;

import com.example.backend.dto.WeatherAPI.WeatherDTO;

public interface WeatherService {
    WeatherDTO getCurrentWeather(String cityName);

}
