package com.example.trip_manager.services;

import com.example.trip_manager.models.City;
import com.example.trip_manager.repositories.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    private City testCity;

    @BeforeEach
    public void setup() {
        testCity = new City();
        testCity.setId(1L);
        testCity.setName("Paris");
        testCity.setCountry("France");
        testCity.setDetails("City of Light - vibrant culture and incredible food");
    }

    @Test
    void testGetAllCitiesHappyCase() {
        List<City> cities = new ArrayList<>();
        cities.add(testCity);
        when(cityRepository.findAll()).thenReturn(cities);

        List<City> result = cityService.getAllCities();

        assertEquals(1, result.size());
        assertEquals("Paris", result.get(0).getName());
        verify(cityRepository, times(1)).findAll();
    }

    @Test
    public void testGetCityByIdHappyCase() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(testCity));

        City result = cityService.getCityById(1L);

        assertNotNull(result);
        assertEquals("Paris", result.getName());
        assertEquals("France", result.getCountry());
    }

    @Test
    public void testGetCityById_NotFound() {
        when(cityRepository.findById(999L)).thenReturn(Optional.empty());


        assertThrows(ResponseStatusException.class, () -> {
            cityService.getCityById(999L);
        });
    }

    @Test
    public void testAddCity() {
        when(cityRepository.save(testCity)).thenReturn(testCity);

        City result = cityService.addCity(testCity);

        assertNotNull(result);
        assertEquals("Paris", result.getName());
        verify(cityRepository, times(1)).save(testCity);
    }

    @Test
    public void testUpdateCityHappyCase() {
        City updatedCity = new City();
        updatedCity.setName("Paris Updated");
        updatedCity.setCountry("France");
        updatedCity.setDetails("Updated description");

        when(cityRepository.findById(1L)).thenReturn(Optional.of(testCity));
        when(cityRepository.save(any(City.class))).thenReturn(testCity);

        City result = cityService.updateCity(1L, updatedCity);

        assertNotNull(result);
        verify(cityRepository, times(1)).findById(1L);
        verify(cityRepository, times(1)).save(any(City.class));
    }

    @Test
    public void testUpdateCity_NotFound() {
        City updatedCity = new City();
        updatedCity.setName("Test");
        when(cityRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            cityService.updateCity(999L, updatedCity);
        });
    }

    @Test
    public void testDeleteCityHappyCase() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(testCity));

        cityService.deleteCity(1L);

        verify(cityRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCity_NotFound() {
        when(cityRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            cityService.deleteCity(999L);
        });
    }
}
