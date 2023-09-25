package com.example.backend.controller;

import com.example.backend.dto.WeatherAPI.WeatherDTO;
import com.example.backend.model.entity.WeatherNotes;
import com.example.backend.service.NotesService;
import com.example.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotesController {
    private final NotesService notesService;
    private static final Logger logger = LoggerFactory.getLogger(NotesController.class);

    @GetMapping("/super/notes")
    public List<WeatherNotes> getAllNotes() {
        logger.info("getAllNotes()");
        return notesService.getAllNotes();
    }

    @GetMapping("/super/{city}/notes")
    public List<WeatherNotes> noteForCity(@PathVariable String city) {
        logger.info("noteForCity()");
        logger.debug("City Name : {}", city);
        return notesService.getAllCityNotes(city);
    }


    @GetMapping("/notes/{adminId}")
    public List<WeatherNotes> notesForAdmin(@PathVariable Integer adminId) {
        logger.info("notesForAdmin()");
        logger.debug("Admin Id : {} ", adminId);
        return notesService.getAllNotesToAdmin(adminId);
    }

    @PostMapping("/notes/{adminId}")
    public ResponseEntity<WeatherNotes> addWeatherNotes(@PathVariable Integer adminId, @RequestBody WeatherNotes noteValue) {
        logger.info("addWeatherNotes()");
        logger.debug("adminId: {}, weatherNote: {}", adminId, noteValue);
        return ResponseEntity.ok(notesService.addWeatherNote(adminId, noteValue));
    }

    @PutMapping("/notes/{noteId}")
    public ResponseEntity<WeatherNotes> updatedWeatherNotes(
            @RequestBody WeatherNotes noteValue, @PathVariable Integer noteId) {
        logger.info("updatedWeatherNotes()");
        logger.debug(" weatherNote: {} , noteId: {}", noteValue,noteId);
        return ResponseEntity.ok(notesService.updateWeatherNote(noteId, noteValue));
    }

    //    @PathParam("cityName") String cityName
    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<String> deleteWeatherNotes(@PathVariable Integer noteId) {
        notesService.deleteWeatherNote(noteId);

        logger.info("deleteWeatherNotes()");
        logger.debug(" noteId: {}",noteId);
        return ResponseEntity.ok("Note with id " + noteId + " has been deleted ");
    }

}
