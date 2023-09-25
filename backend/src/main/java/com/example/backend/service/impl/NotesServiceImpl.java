package com.example.backend.service.impl;

import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.exceptions.WeatherNoteNotFoundException;
import com.example.backend.model.entity.User;
import com.example.backend.model.entity.WeatherNotes;
import com.example.backend.model.repo.UserRepo;
import com.example.backend.model.repo.WeatherNotesRepo;
import com.example.backend.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class NotesServiceImpl implements NotesService {

    private final WeatherNotesRepo weatherNotesRepo;
    private final DateTimeFormatter dateTimeFormatter;
    private final UserRepo userRepo;
    private static final Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);


    @Autowired
    public NotesServiceImpl(WeatherNotesRepo weatherNotesRepo, UserRepo userRepo) {
        this.weatherNotesRepo = weatherNotesRepo;
        this.userRepo = userRepo;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    }

    @Override
    public List<WeatherNotes> getAllNotes() {
        logger.info("getAllNotes() called");
        return weatherNotesRepo.findAll();
    }

    @Override
    public List<WeatherNotes> getAllCityNotes(String cityName) {
        logger.info("getAllCityNotes() called");
        logger.debug("cityName: {},", cityName);
        return weatherNotesRepo.findAllByCityName(cityName);

    }

    @Override
    public List<WeatherNotes> getAllNotesToAdmin(Integer adminId) {
        logger.info("getAllNotesToAdmin() called");
        logger.debug("adminId: {},", adminId);
        Optional<User> optionalUser = userRepo.findById(adminId);
        if (optionalUser.isPresent()) {
            return optionalUser.get().getNotes();
        }
        throw new UserNotFoundException("admin id " + " is not found ");

    }

    @Override
    public WeatherNotes addWeatherNote(Integer adminId, WeatherNotes weatherNote) {
        logger.info("addWeatherNote() called");
        logger.debug("adminId: {}, weatherNote: {}", adminId, weatherNote);
        Optional<User> optionalUser = userRepo.findById(adminId);
        if (optionalUser.isPresent()) {
            WeatherNotes updatedNote = new WeatherNotes();
            updatedNote.setAdminName(optionalUser.get().getName());
            updatedNote.setUser(optionalUser.get());
            updatedNote.setCityName(weatherNote.getCityName());
            updatedNote.setDate(date());
            updatedNote.setNote(weatherNote.getNote());
            weatherNotesRepo.save(updatedNote);
            return updatedNote;
        } else
            throw new UserNotFoundException("no user have inserted this note !!!!");

    }


    @Override
    public WeatherNotes updateWeatherNote(Integer noteId, WeatherNotes weatherNote) {
        logger.info("updateWeatherNote() called");
        logger.debug("noteId: {}, weatherNote: {}  ", noteId, weatherNote);
        Optional<WeatherNotes> updatedNote = weatherNotesRepo.findById(noteId);
        if (updatedNote.isPresent()) {
            updatedNote.get().setNote(weatherNote.getNote());
            updatedNote.get().setCityName(weatherNote.getCityName());
            updatedNote.get().setDate(date());
            weatherNotesRepo.save(updatedNote.get());
            return updatedNote.get();
        } else
            throw new WeatherNoteNotFoundException("Note with id " + noteId + " not found.");
    }


    @Override
    public void deleteWeatherNote(Integer noteId) {
        logger.info("deleteWeatherNote() called");
        logger.debug("noteId: {}", noteId);
        Optional<WeatherNotes> deletedNote = weatherNotesRepo.findById(noteId);
        if (deletedNote.isPresent()) {
            weatherNotesRepo.delete(deletedNote.get());
        } else {
            throw new WeatherNoteNotFoundException("Note with id " + noteId + " not found.");
        }
    }

    public WeatherNotes getDefaultWeatherNotes(Float temp, String cityName) {
        String weatherNote = getDefaultWeatherNote(temp);
        WeatherNotes defaultNotes = new WeatherNotes();
        defaultNotes.setCityName(cityName);
        defaultNotes.setDate(date());
        defaultNotes.setNote(weatherNote);
        weatherNotesRepo.save(defaultNotes);
        return defaultNotes;

    }

    private static String getDefaultWeatherNote(float temp) {
        String weatherNote;
        if (temp > 0 && temp <= 20) {
            weatherNote = "It's too cold today. Try to stay indoors if you can.";
        } else if (temp > 20 && temp <= 30) {
            weatherNote = "It's a good day today. Try to get some rest and enjoy. ";
        } else if (temp > 30 && temp <= 40) {

            weatherNote = "It's a bit sunny today. Remember to wear a cap.";
        } else if (temp > 40) {
            weatherNote = "It's too hot today. Try to avoid going outside if possible.";
        } else {
            weatherNote = null;
        }
        return weatherNote;
    }


    private String date() {
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }


}
