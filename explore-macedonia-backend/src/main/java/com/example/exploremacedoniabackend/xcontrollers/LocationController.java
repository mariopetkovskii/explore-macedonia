package com.example.exploremacedoniabackend.xcontrollers;

import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.helpers.LocationHelper;
import com.example.exploremacedoniabackend.models.userroles.helpers.UserRegisterDto;
import com.example.exploremacedoniabackend.service.places.interfaces.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/location")
@AllArgsConstructor
public class LocationController {
    private final LocationService locationService;
    @PostMapping("/add")
    public ResponseEntity<String> addLocation(@RequestBody LocationHelper locationHelper){
        return this.locationService.addLocation(locationHelper)
                .map(user -> ResponseEntity.ok().body("Location was successfully added."))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/getAll")
    public List<Location> getLocations(){
        return this.locationService.getAll();
    }
}
