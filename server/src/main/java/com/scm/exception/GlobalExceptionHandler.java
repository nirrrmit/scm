package com.scm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleDuplicatedUser(DuplicateUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> handleInvalidData(InvalidDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data provided: " + ex.getMessage());
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<String> handleUserNotFound(UserDoesNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity does not exist: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
