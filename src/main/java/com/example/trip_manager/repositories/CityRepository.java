package com.example.trip_manager.repositories;

import com.example.trip_manager.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

}
