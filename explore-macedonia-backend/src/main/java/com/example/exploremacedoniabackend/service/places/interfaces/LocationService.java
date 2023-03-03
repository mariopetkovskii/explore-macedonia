package com.example.exploremacedoniabackend.service.places.interfaces;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.helpers.LocationHelper;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    Location findByName(String name);
    Optional<Location> addLocation(LocationHelper locationHelper);
    Optional<Location> editLocation(Long id, LocationHelper locationHelper);
    void deleteLocation(LocationHelper locationHelper);
    Location findById(Long id);
    Optional<Location> recommendLocation(Long id, LocationHelper locationHelper);
    List<Location> getAll();
}
