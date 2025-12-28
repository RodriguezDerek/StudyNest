package com.example.backend.exception;

public class InvalidDayOfWeekException extends RuntimeException {
    public InvalidDayOfWeekException(String message) {
        super(message);
    }
}
