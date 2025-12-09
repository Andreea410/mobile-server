package com.example.trips_server.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class TripExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(TripExceptionHandler.class);

    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<String> handleNotFound(TripNotFoundException ex) {
        log.warn("[TripExceptionHandler] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The requested trip was not found.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        log.error("[TripExceptionHandler] Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong on the server. Please try again later.");
    }
}
