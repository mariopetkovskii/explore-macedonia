package com.example.exploremacedoniabackend.xcontrollers;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.entity.UnvisitedLocation;
import com.example.exploremacedoniabackend.models.places.helpers.LocationHelper;
import com.example.exploremacedoniabackend.models.userroles.helpers.UserRegisterDto;
import com.example.exploremacedoniabackend.service.places.interfaces.LocationService;
import com.example.exploremacedoniabackend.service.places.interfaces.UnvisitedLocationService;
import com.example.exploremacedoniabackend.service.places.interfaces.VisitedLocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest/location")
@AllArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final VisitedLocationService visitedLocationService;
    private final UnvisitedLocationService unvisitedLocationService;
    @PostMapping("/add")
    public ResponseEntity<String> addLocation(@RequestBody LocationHelper locationHelper){
        return this.locationService.addLocation(locationHelper)
                .map(user -> ResponseEntity.ok().body("Location was successfully added."))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editLocation(@PathVariable Long id, @RequestBody LocationHelper locationHelper){
        return this.locationService.editLocation(id, locationHelper)
                .map(user -> ResponseEntity.ok().body("Edit."))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @GetMapping("/recommend/{id}")
    public ResponseEntity<String> recommendLocation(@PathVariable Long id, @RequestBody LocationHelper locationHelper){
        return this.locationService.recommendLocation(id, locationHelper)
                .map(user -> ResponseEntity.ok().body("Recommend."))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteLocation(@RequestBody LocationHelper locationHelper){
        this.locationService.deleteLocation(locationHelper);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public List<Location> getLocations(){
        return this.locationService.getAll();
    }

    @GetMapping("/unvisitLocation")
    public List<Location> getAllUnvisitedLocationsForUser(HttpServletRequest request) {
        return unvisitedLocationService.getAll(request);

    }
    @GetMapping("/visitLocation")
    public List<Location> getAllVisitedLocationsForUser(HttpServletRequest request) {
        return visitedLocationService.getAll(request);

    }
}
