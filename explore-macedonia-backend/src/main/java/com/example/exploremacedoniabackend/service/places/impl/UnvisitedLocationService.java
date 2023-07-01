package com.example.exploremacedoniabackend.service.places.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.exploremacedoniabackend.models.places.entity.Location;
import com.example.exploremacedoniabackend.models.places.entity.UnvisitedLocation;
import com.example.exploremacedoniabackend.models.userroles.entity.User;
import com.example.exploremacedoniabackend.repository.places.UnvisitedLocationRepository;
import com.example.exploremacedoniabackend.service.userroles.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.exploremacedoniabackend.security.SecurityConstants.*;

@Service
@AllArgsConstructor
public class UnvisitedLocationService implements com.example.exploremacedoniabackend.service.places.interfaces.UnvisitedLocationService {
    private UnvisitedLocationRepository unvisitedLocationRepository;
    private UserService userService;
    @Override
    public List<Location> getAll(HttpServletRequest request) {
        String token=request.getHeader(HEADER_STRING);
        if(token!=null)
        {
            String email= JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();
            User user=userService.findByEmail(email);
            return this.unvisitedLocationRepository.findAllLocationsByUser(user);
        }
        return null;
    }
}
