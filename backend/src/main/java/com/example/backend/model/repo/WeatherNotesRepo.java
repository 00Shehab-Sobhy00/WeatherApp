package com.example.backend.model.repo;

import com.example.backend.model.entity.WeatherNotes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherNotesRepo extends JpaRepository<WeatherNotes,Integer> {

    List<WeatherNotes> findAllByCityName (String cityName);


}
