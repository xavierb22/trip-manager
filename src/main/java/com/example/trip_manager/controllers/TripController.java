package com.example.trip_manager.controllers;

import com.example.trip_manager.models.Trip;
import com.example.trip_manager.services.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {
    private final TripService tripservice;

    public TripController(TripService tripservice){
        this.tripservice = tripservice;
    }

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripservice.getAllTrips();
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id)
    {
        return tripservice.getTripById(id);
    }

    @PostMapping
    public ResponseEntity<Trip> addTrip(@RequestBody Trip trip) {
        Trip savedTrip = tripservice.addTrip(trip);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTrip.getTrip_id())
                .toUri();

        return ResponseEntity.created(location).body(savedTrip);
    }

    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
        return tripservice.updateTrip(id, trip);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripservice.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
