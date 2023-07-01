package com.example.exploremacedoniabackend.service.places.interfaces;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.entity.VisitedLocation;
import com.example.exploremacedoniabackend.models.places.helpers.VisitedOrUnvisitedLocationHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface VisitedLocationService {
    void visitLocation(VisitedOrUnvisitedLocationHelper visitedOrUnvisitedLocationHelper);
    List<Location> getAll(HttpServletRequest request);
}
