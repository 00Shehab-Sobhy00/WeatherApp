package com.example.backend.service;

import com.example.backend.dto.WeatherAPI.WeatherDTO;
import com.example.backend.model.entity.WeatherNotes;

import java.util.List;

public interface NotesService {


    List<WeatherNotes> getAllNotes();
    List<WeatherNotes> getAllCityNotes(String cityName);

    List <WeatherNotes> getAllNotesToAdmin(Integer adminId);
    WeatherNotes addWeatherNote(Integer adminId ,WeatherNotes weatherNote);
    WeatherNotes updateWeatherNote ( Integer noteId , WeatherNotes weatherNote);

     WeatherNotes getDefaultWeatherNotes(Float temp , String cityName);
     void deleteWeatherNote(Integer noteId);
}
