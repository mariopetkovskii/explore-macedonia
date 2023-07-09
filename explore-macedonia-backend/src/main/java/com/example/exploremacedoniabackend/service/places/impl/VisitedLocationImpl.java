package com.example.exploremacedoniabackend.service.places.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.entity.UnvisitedLocation;
import com.example.exploremacedoniabackend.models.places.entity.VisitedLocation;
import com.example.exploremacedoniabackend.models.places.exceptions.LocationNotFoundException;
import com.example.exploremacedoniabackend.models.places.helpers.VisitedOrUnvisitedLocationHelper;
import com.example.exploremacedoniabackend.models.userroles.entity.User;
import com.example.exploremacedoniabackend.models.userroles.exceptions.UserNotExistsException;
import com.example.exploremacedoniabackend.repository.places.LocationRepository;
import com.example.exploremacedoniabackend.repository.places.UnvisitedLocationRepository;
import com.example.exploremacedoniabackend.repository.places.VisitedLocationRepository;
import com.example.exploremacedoniabackend.repository.userroles.UserRepository;
import com.example.exploremacedoniabackend.service.places.interfaces.VisitedLocationService;
import com.example.exploremacedoniabackend.service.userroles.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.example.exploremacedoniabackend.security.SecurityConstants.*;

@Service
@AllArgsConstructor
public class VisitedLocationImpl implements VisitedLocationService {
    private final VisitedLocationRepository visitedLocationRepository;
    private final UnvisitedLocationRepository unvisitedLocationRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private UserService userService;

    @Override
    public void visitLocation(VisitedOrUnvisitedLocationHelper visitedOrUnvisitedLocationHelper) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = this.userRepository.findByEmail(authentication.getName());
        Location location = this.locationRepository.findById(visitedOrUnvisitedLocationHelper.getLocationId()).orElseThrow(LocationNotFoundException::new);
        this.visitedLocationRepository.save(new VisitedLocation(user, location));
        UnvisitedLocation unvisitedLocation = this.unvisitedLocationRepository.findByUserAndLocation(user, location);
        this.unvisitedLocationRepository.deleteById(unvisitedLocation.getId());
    }

    @Override
    public List<Location> getAll(HttpServletRequest request) {
        String token=request.getHeader(HEADER_STRING);
        if(token!=null)
        {
            String email= JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();
            User user=userService.findByEmail(email);
            return this.visitedLocationRepository.findAllLocationsByUser(user);
        }
        return null;
    }
}
