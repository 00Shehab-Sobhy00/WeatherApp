package com.example.backend.exceptions;

public class InternalErrorsException extends RuntimeException {
    public InternalErrorsException(String msg) {
        super(msg);
    }
}
