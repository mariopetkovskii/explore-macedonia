package com.example.exploremacedoniabackend.repository.places;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.entity.UnvisitedLocation;
import com.example.exploremacedoniabackend.models.userroles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnvisitedLocationRepository extends JpaRepository<UnvisitedLocation, Long> {
    UnvisitedLocation findByUserAndLocation(User user, Location location);
    @Query("SELECT ul.location FROM UnvisitedLocation ul WHERE ul.user = :user")
    List<Location> findAllLocationsByUser(@Param("user") User user);
//    List<UnvisitedLocation> findAllByUser(User user);
}
