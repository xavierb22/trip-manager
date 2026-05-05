package com.example.trip_manager.services;

import com.example.trip_manager.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TripService {
    @Autowired
    private TripRepository tripRepository;


}
