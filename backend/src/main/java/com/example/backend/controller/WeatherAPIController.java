package com.example.backend.controller;

import com.example.backend.dto.WeatherAPI.WeatherDTO;
import com.example.backend.service.WeatherService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class WeatherAPIController {

    private final WeatherService weatherService;
    private static final Logger logger = LoggerFactory.getLogger(WeatherAPIController.class);

    @GetMapping("/info/{cityName}")
    public ResponseEntity<WeatherDTO> getWeatherData(@PathVariable String cityName) {
        logger.info("getWeatherData()");
        logger.debug("cityName : {} ", cityName);
        return ResponseEntity.ok(weatherService.getCurrentWeather(cityName));
    }


}