package com.example.trips_server.services;

import com.example.trips_server.dtos.TripRequest;
import com.example.trips_server.exceptions.TripNotFoundException;
import com.example.trips_server.models.Trip;
import com.example.trips_server.repositories.TripRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private static final Logger log = LoggerFactory.getLogger(TripService.class);

    private final TripRepository repository;

    public List<Trip> getAllTrips() {
        log.debug("[TripService] Fetching all trips");
        return repository.findAll();
    }

    public Trip getTrip(Long id) {
        log.debug("[TripService] Fetching trip id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new TripNotFoundException(id));
    }

    public Trip createTrip(TripRequest request) {
        log.info("[TripService] Creating trip name={}", request.getName());
        Trip trip = Trip.builder()
                .name(request.getName())
                .destination(request.getDestination())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .totalBudget(request.getTotalBudget())
                .notes(request.getNotes())
                .build();
        return repository.save(trip);
    }

    public Trip updateTrip(Long id, TripRequest request) {
        log.info("[TripService] Updating trip id={}", id);
        Trip existing = getTrip(id);

        existing.setName(request.getName());
        existing.setDestination(request.getDestination());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        existing.setTotalBudget(request.getTotalBudget());
        existing.setNotes(request.getNotes());

        return repository.save(existing);
    }

    public void deleteTrip(Long id) {
        log.info("[TripService] Deleting trip id={}", id);
        if (!repository.existsById(id)) {
            throw new TripNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
