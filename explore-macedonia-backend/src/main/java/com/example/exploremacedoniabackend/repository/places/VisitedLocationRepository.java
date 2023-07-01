package com.example.exploremacedoniabackend.repository.places;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.entity.VisitedLocation;
import com.example.exploremacedoniabackend.models.userroles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitedLocationRepository extends JpaRepository<VisitedLocation, Long> {
    List<User> findAllUsersByLocation(Location location);
    @Query("SELECT vl.location FROM VisitedLocation vl WHERE vl.user = :user")
    List<Location> findAllLocationsByUser(@Param("user") User user);

}
