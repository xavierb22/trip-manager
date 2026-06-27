package com.example.trip_manager.services;

import com.example.trip_manager.models.City;
import com.example.trip_manager.models.Trip;
import com.example.trip_manager.repositories.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripTestService {
    @Mock
    private TripRepository tripRepository;
    @InjectMocks
    private TripService tripService;

    private Trip testTrip;
    private City testCity;

    @BeforeEach
    public void setup() {
        // Create a test city (Trip has a relationship with City)
        testCity = new City();
        testCity.setId(1L);
        testCity.setName("Paris");
        testCity.setCountry("France");
        testCity.setDetails("City of Light");

        // Create test trip data
        testTrip = new Trip();
        testTrip.setTrip_id(1L);
        testTrip.setCity(testCity);
        testTrip.setStartDate(LocalDate.of(2024, 6, 1));
        testTrip.setEndDate(LocalDate.of(2024, 6, 10));
        testTrip.setRating(5);
        testTrip.setPersonalNotes("Amazing trip to Paris!");
    }

    @Test
    public void testFindAll() {
        List<Trip> trips = new ArrayList<>();
        trips.add(testTrip);
        when(tripRepository.findAll()).thenReturn(trips);

        List<Trip> result = tripService.getAllTrips();

        assertEquals(1, result.size());
        assertEquals("Paris", result.get(0).getCity().getName());
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(tripRepository.findById(1L)).thenReturn(Optional.of(testTrip));

        Trip result = tripService.getTripById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getTrip_id());
        assertEquals("Paris", result.getCity().getName());
        assertEquals(5, result.getRating());
        assertEquals("Amazing trip to Paris!", result.getPersonalNotes());
    }

    @Test
    public void testGetTripById_NotFound() {
        when(tripRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            tripService.getTripById(999L);
        });
    }

    @Test
    public void testAddTrip() {
        when(tripRepository.save(testTrip)).thenReturn(testTrip);

        Trip result = tripService.addTrip(testTrip);

        assertNotNull(result);
        assertEquals(1L, result.getTrip_id());
        assertEquals("Paris", result.getCity().getName());
    }

    @Test
    public void testUpdateTripHappyCase() {
        Trip updatedTrip = new Trip();
        updatedTrip.setCity(testCity);
        updatedTrip.setStartDate(LocalDate.of(2024, 7, 1));
        updatedTrip.setEndDate(LocalDate.of(2024, 7, 15));
        updatedTrip.setRating(4);
        updatedTrip.setPersonalNotes("Updated notes - great experience!");

        when(tripRepository.findById(1L)).thenReturn(Optional.of(testTrip));
        when(tripRepository.save(any(Trip.class))).thenReturn(testTrip);

        Trip result = tripService.updateTrip(1L, updatedTrip);

        assertNotNull(result);
        verify(tripRepository, times(1)).findById(1L);
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    public void testUpdateTrip_NotFound() {
        Trip updatedTrip = new Trip();
        updatedTrip.setRating(3);
        when(tripRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            tripService.updateTrip(999L, updatedTrip);
        });
    }

    @Test
    public  void testDeleteTripHappyCase() {
        when(tripRepository.findById(1L)).thenReturn(Optional.of(testTrip));

        tripService.deleteTrip(1L);

        verify(tripRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTrip_NotFound() {
        when(tripRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            tripService.deleteTrip(999L);
        });
    }
}
