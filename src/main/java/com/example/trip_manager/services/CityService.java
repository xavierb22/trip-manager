package com.example.trip_manager.services;

import com.example.trip_manager.models.City;
import com.example.trip_manager.repositories.CityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "City not found with id: " + id
                ));
    }

    public City addCity(City city) {
        return cityRepository.save(city);
    }

    public City updateCity(Long id, City updatedCity) {
        City existingCity = cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "City not found with id: " + id
                ));

        existingCity.setName(updatedCity.getName());
        existingCity.setCountry(updatedCity.getCountry());
        existingCity.setDetails(updatedCity.getDetails());

        return cityRepository.save(existingCity);
    }

    public void deleteCity(Long id) {
        cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "City not found with id: " + id
                ));

        cityRepository.deleteById(id);
    }
}