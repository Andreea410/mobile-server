package com.example.trips_server.exceptions;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(Long id) {
        super("Trip with id " + id + " not found");
    }
}
