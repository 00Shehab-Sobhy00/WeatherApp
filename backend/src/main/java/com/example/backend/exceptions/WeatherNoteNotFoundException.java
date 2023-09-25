package com.example.backend.exceptions;

public class WeatherNoteNotFoundException extends RuntimeException {
    public WeatherNoteNotFoundException(String msg) {
        super(msg);
    }
}

