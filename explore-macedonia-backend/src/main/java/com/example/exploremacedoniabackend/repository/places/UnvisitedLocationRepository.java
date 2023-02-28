package com.example.exploremacedoniabackend.repository.places;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.entity.UnvisitedLocation;
import com.example.exploremacedoniabackend.models.userroles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnvisitedLocationRepository extends JpaRepository<UnvisitedLocation, Long> {
    UnvisitedLocation findByUserAndLocation(User user, Location location);
}
