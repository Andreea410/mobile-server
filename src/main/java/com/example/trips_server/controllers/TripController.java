package com.example.trips_server.controllers;

import com.example.trips_server.dtos.TripRequest;
import com.example.trips_server.dtos.TripResponse;
import com.example.trips_server.models.Trip;
import com.example.trips_server.services.TripService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TripController {

    private static final Logger log = LoggerFactory.getLogger(TripController.class);

    private final TripService service;

    // READ all
    @GetMapping
    public List<TripResponse> getAllTrips() {
        log.debug("[TripController] GET /api/trips");
        return service.getAllTrips().stream()
                .map(TripResponse::fromEntity)
                .toList();
    }

    // READ one
    @GetMapping("/{id}")
    public TripResponse getTrip(@PathVariable Long id) {
        log.debug("[TripController] GET /api/trips/{}", id);
        Trip trip = service.getTrip(id);
        return TripResponse.fromEntity(trip);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@RequestBody TripRequest request) {
        log.debug("[TripController] POST /api/trips name={}", request.getName());
        Trip created = service.createTrip(request);
        TripResponse response = TripResponse.fromEntity(created);

        return ResponseEntity
                .created(URI.create("/api/trips/" + created.getId()))
                .body(response);
    }

    // UPDATE
    @PutMapping("/{id}")
    public TripResponse updateTrip(@PathVariable Long id,
                                   @RequestBody TripRequest request) {
        log.debug("[TripController] PUT /api/trips/{}", id);
        Trip updated = service.updateTrip(id, request);
        return TripResponse.fromEntity(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        log.debug("[TripController] DELETE /api/trips/{}", id);
        service.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
