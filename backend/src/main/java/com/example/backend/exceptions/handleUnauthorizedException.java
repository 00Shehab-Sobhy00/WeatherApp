package com.example.backend.exceptions;

public class handleUnauthorizedException extends RuntimeException {
    public handleUnauthorizedException(String msg) {
        super(msg);
    }
}
