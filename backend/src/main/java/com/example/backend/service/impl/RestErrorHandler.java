package com.example.backend.service.impl;

import com.example.backend.dto.ErrorMessageDTO;
import com.example.backend.exceptions.InternalErrorsException;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.exceptions.WeatherNoteNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {
    @ExceptionHandler({UserNotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageDTO handleUserNotFoundException(UserNotFoundException ex) {
        return new ErrorMessageDTO(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
    }

    @ExceptionHandler(WeatherNoteNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageDTO handleWeatherNoteNotFoundException(WeatherNoteNotFoundException ex) {
        return new ErrorMessageDTO(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessageDTO handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return new ErrorMessageDTO(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage());
    }

    @ExceptionHandler(InternalErrorsException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessageDTO handleInternalErrorsException(InternalErrorsException ex) {
        return new ErrorMessageDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
    }
}



