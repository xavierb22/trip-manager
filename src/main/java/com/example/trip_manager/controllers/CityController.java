package com.example.trip_manager.controllers;

import com.example.trip_manager.models.City;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.trip_manager.services.CityService;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityservice;

    public CityController(CityService cityService) {
        this.cityservice = cityService;
    }

    @GetMapping
    public List<City> getAllCities() {
        return cityservice.getAllCities();
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        return cityservice.getCityById(id);
    }

    @PostMapping
    public ResponseEntity<City> addCity(@RequestBody City city) {

        City savedCity = cityservice.addCity(city);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCity.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedCity);
    }

    @PutMapping("/{id}")
    public City updateCity(@PathVariable Long id, @RequestBody City city) {
        return cityservice.updateCity(id, city);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityservice.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}