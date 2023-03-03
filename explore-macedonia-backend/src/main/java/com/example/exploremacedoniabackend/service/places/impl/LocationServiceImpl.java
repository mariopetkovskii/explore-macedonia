package com.example.exploremacedoniabackend.service.places.impl;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.entity.UnvisitedLocation;
import com.example.exploremacedoniabackend.models.places.exceptions.LocationNotFoundException;
import com.example.exploremacedoniabackend.models.places.helpers.LocationHelper;
import com.example.exploremacedoniabackend.models.userroles.entity.User;
import com.example.exploremacedoniabackend.repository.places.LocationRepository;
import com.example.exploremacedoniabackend.repository.places.UnvisitedLocationRepository;
import com.example.exploremacedoniabackend.service.places.interfaces.LocationService;
import com.example.exploremacedoniabackend.service.userroles.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final UnvisitedLocationRepository unvisitedLocationRepository;
    private final UserService userService;
    @Override
    public Location findByName(String location) {
        return this.locationRepository.findByLocation(location);
    }

    @Override
    public Optional<Location> addLocation(LocationHelper locationHelper) {
        List<User> allUsers = this.userService.findAll();
        Location location = this.locationRepository.findByLocation(locationHelper.getLocation());
        this.locationRepository.save(new Location(
                locationHelper.getLocation(),
                locationHelper.getLongitude(),
                locationHelper.getLatitude(),
                locationHelper.getDescription()
        ));
        allUsers.forEach(user -> {
            this.unvisitedLocationRepository.save(new UnvisitedLocation(user, location));
        });
        return Optional.of(location);
    }

    @Override
    public Optional<Location> editLocation(Long id, LocationHelper locationHelper) {
        Location location = this.locationRepository.findById(id).orElseThrow(LocationNotFoundException::new);
        location.setLocation(locationHelper.getLocation());
        location.setDescription(locationHelper.getDescription());
        location.setLongitude(locationHelper.getLongitude());
        location.setLatitude(locationHelper.getLatitude());
        return Optional.of(this.locationRepository.save(location));
    }

    @Override
    public void deleteLocation(LocationHelper locationHelper) {
        Location location = this.locationRepository.findByLocation(locationHelper.getLocation());
        this.locationRepository.deleteById(location.getId());
    }

    @Override
    public Location findById(Long id) {
        return this.locationRepository.findById(id).orElseThrow(LocationNotFoundException::new);
    }

    @Override
    public Optional<Location> recommendLocation(Long id, LocationHelper locationHelper) {
        Location location = this.findById(id);
        location.setIsRecommended(true);
        return Optional.of(this.locationRepository.save(location));
    }

    @Override
    public List<Location> getAll() {
        return this.locationRepository.findAll();
    }
}
