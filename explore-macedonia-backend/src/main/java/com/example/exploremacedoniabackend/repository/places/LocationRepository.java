package com.example.exploremacedoniabackend.repository.places;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByLocation(String location);
}
