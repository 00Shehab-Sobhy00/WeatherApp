package com.example.backend.service.impl;

import com.example.backend.dto.WeatherAPI.WeatherDTO;
import com.example.backend.dto.WeatherAPI.WeatherJson;
import com.example.backend.exceptions.WeatherNoteNotFoundException;
import com.example.backend.model.repo.WeatherNotesRepo;
import com.example.backend.service.NotesService;
import com.example.backend.service.WeatherService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.format.DateTimeFormatter;


@Service
@Data
public class WeatherServiceImpl implements WeatherService {

    private RestTemplate restTemplate;
    private WeatherNotesRepo weatherNotesRepo;
    private String cityNAME;
    private Float temp;
    private DateTimeFormatter dateTimeFormatter;
    private NotesService notesService;
    private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Autowired
    public WeatherServiceImpl(RestTemplate restTemplate, WeatherNotesRepo weatherNotesRepo,NotesService notesService ) {
        this.restTemplate = restTemplate;
        this.weatherNotesRepo = weatherNotesRepo;
//        this.weatherNotesMap = new HashMap<>();
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.notesService = notesService;
    }

    public WeatherServiceImpl() {

    }

    @Override
    public WeatherDTO getCurrentWeather(String cityName) {
        logger.info("getCurrentWeather()");
        logger.debug("cityName :{}",cityName);
        if (cityName == null) {
            cityName = "cairo";
        }
        cityNAME = cityName;
        String apiUrl = "https://api.weatherapi.com/v1/current.json?key=005e653ed0dc4d35ac7192041200411&q=" + cityNAME + "&aqi=yes";
        WeatherJson weatherDetails = restTemplate.getForObject(apiUrl, WeatherJson.class);
        var currentWeather = new WeatherDTO();
        if (weatherDetails != null) {
            currentWeather.setCurrentWeatherCondition(weatherDetails.getCurrent().getCondition().getText());
            currentWeather.setIcon(weatherDetails.getCurrent().getCondition().getIcon());
            currentWeather.setTemperature(weatherDetails.getCurrent().getTemp_c());
        } else {
            logger.error("weather data didn't came for unknown reasons ");
            throw new WeatherNoteNotFoundException("weather data didn't came for unknown reasons");
        }

        if (weatherNotesRepo.findAllByCityName(cityNAME).isEmpty()) {
            weatherNotesRepo.save(notesService.getDefaultWeatherNotes(Float.parseFloat(currentWeather.getTemperature()),cityNAME));
        }
        currentWeather.setWeatherNotes(weatherNotesRepo.findAllByCityName(cityNAME));
        return currentWeather;
    }


}
