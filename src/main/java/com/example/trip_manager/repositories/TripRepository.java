package com.example.trip_manager.repositories;

import com.example.trip_manager.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
