package com.example.exploremacedoniabackend.service.places.interfaces;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.entity.UnvisitedLocation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UnvisitedLocationService {
    List<Location> getAll(HttpServletRequest request);
}
