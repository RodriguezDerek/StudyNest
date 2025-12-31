package com.example.backend.exception;

public class AssignmentExistsException extends RuntimeException {
    public AssignmentExistsException(String message) {
        super(message);
    }
}
