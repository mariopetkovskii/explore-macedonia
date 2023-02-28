package com.example.exploremacedoniabackend.service.places.interfaces;

import com.example.exploremacedoniabackend.models.places.entity.VisitedLocation;
import com.example.exploremacedoniabackend.models.places.helpers.VisitedOrUnvisitedLocationHelper;

import java.util.Optional;

public interface VisitedLocationService {
    void visitLocation(VisitedOrUnvisitedLocationHelper visitedOrUnvisitedLocationHelper);
}
