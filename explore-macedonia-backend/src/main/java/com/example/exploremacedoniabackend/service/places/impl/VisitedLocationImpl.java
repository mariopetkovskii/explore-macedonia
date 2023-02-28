package com.example.exploremacedoniabackend.service.places.impl;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.entity.UnvisitedLocation;
import com.example.exploremacedoniabackend.models.places.entity.VisitedLocation;
import com.example.exploremacedoniabackend.models.places.exceptions.LocationNotFoundException;
import com.example.exploremacedoniabackend.models.places.helpers.VisitedOrUnvisitedLocationHelper;
import com.example.exploremacedoniabackend.models.userroles.entity.User;
import com.example.exploremacedoniabackend.models.userroles.exceptions.UserNotExistsException;
import com.example.exploremacedoniabackend.repository.places.LocationRepository;
import com.example.exploremacedoniabackend.repository.places.UnvisitedLocationRepository;
import com.example.exploremacedoniabackend.repository.places.VisitedLocationRepository;
import com.example.exploremacedoniabackend.repository.userroles.UserRepository;
import com.example.exploremacedoniabackend.service.places.interfaces.VisitedLocationService;
import com.example.exploremacedoniabackend.service.userroles.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VisitedLocationImpl implements VisitedLocationService {
    private final VisitedLocationRepository visitedLocationRepository;
    private final UnvisitedLocationRepository unvisitedLocationRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    @Override
    public void visitLocation(VisitedOrUnvisitedLocationHelper visitedOrUnvisitedLocationHelper) {
        User user = this.userRepository.findById(visitedOrUnvisitedLocationHelper.getUserId()).orElseThrow(UserNotExistsException::new);
        Location location = this.locationRepository.findById(visitedOrUnvisitedLocationHelper.getLocationId()).orElseThrow(LocationNotFoundException::new);
        this.visitedLocationRepository.save(new VisitedLocation(user, location));
        UnvisitedLocation unvisitedLocation = this.unvisitedLocationRepository.findByUserAndLocation(user, location);
        this.unvisitedLocationRepository.deleteById(unvisitedLocation.getId());
    }
}