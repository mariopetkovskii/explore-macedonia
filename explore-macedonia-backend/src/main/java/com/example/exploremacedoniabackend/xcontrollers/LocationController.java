package com.example.exploremacedoniabackend.xcontrollers;

import com.example.exploremacedoniabackend.models.places.helpers.LocationHelper;
import com.example.exploremacedoniabackend.models.userroles.helpers.UserRegisterDto;
import com.example.exploremacedoniabackend.service.places.interfaces.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
