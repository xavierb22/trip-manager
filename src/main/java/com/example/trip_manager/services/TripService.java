package com.example.trip_manager.services;

import com.example.trip_manager.models.City;
import com.example.trip_manager.repositories.TripRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.trip_manager.models.Trip;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TripService {
   private final TripRepository tripRepository;

   public TripService(TripRepository tripRepository) {this.tripRepository = tripRepository;}

    public List<Trip> getAllTrips() {return tripRepository.findAll();}

    public Trip getTripById(Long id){
        return tripRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Trip not found with id: " + id
                ));
    }

    public Trip addTrip(Trip trip){return tripRepository.save(trip);}

    public Trip updateTrip(Long id, Trip updatedTrip) {
        Trip existingTrip = tripRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Trip not found with id: " + id
                ));

        existingTrip.setCity(updatedTrip.getCity());
        existingTrip.setStartDate(updatedTrip.getStartDate());
        existingTrip.setEndDate(updatedTrip.getEndDate());
        existingTrip.setRating(updatedTrip.getRating());
        existingTrip.setPersonalNotes(updatedTrip.getPersonalNotes());


        return tripRepository.save(existingTrip);
    }

    public void deleteTrip(Long id) {
        tripRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Trip not found with id: " + id
                ));

        tripRepository.deleteById(id);
    }
}
