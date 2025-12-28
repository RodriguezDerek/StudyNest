package com.example.backend.exception;

public class CourseExistsException extends RuntimeException {
    public CourseExistsException(String message) {
        super(message);
    }
}
